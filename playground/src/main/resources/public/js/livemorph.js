(() => {
    var __defProp = Object.defineProperty;
    var __getOwnPropNames = Object.getOwnPropertyNames;
    var __getOwnPropDesc = Object.getOwnPropertyDescriptor;
    var __hasOwnProp = Object.prototype.hasOwnProperty;
    var __moduleCache = /* @__PURE__ */ new WeakMap;
    var __toCommonJS = (from) => {
        var entry = __moduleCache.get(from), desc;
        if (entry)
            return entry;
        entry = __defProp({}, "__esModule", {value: true});
        if (from && typeof from === "object" || typeof from === "function")
            __getOwnPropNames(from).map((key) => !__hasOwnProp.call(entry, key) && __defProp(entry, key, {
                get: () => from[key],
                enumerable: !(desc = __getOwnPropDesc(from, key)) || desc.enumerable
            }));
        __moduleCache.set(from, entry);
        return entry;
    };
    var __export = (target, all) => {
        for (var name in all)
            __defProp(target, name, {
                get: all[name],
                enumerable: true,
                configurable: true,
                set: (newValue) => all[name] = () => newValue
            });
    };

    // src/index.js
    var exports_src = {};
    __export(exports_src, {
        default: () => src_default
    });

    // src/protocol.js
    var PROTOCOL_6 = "http://livereload.com/protocols/official-6";
    var PROTOCOL_7 = "http://livereload.com/protocols/official-7";

    class ProtocolError {
        constructor(reason, data) {
            this.message = `LiveReload protocol error (${reason}) after receiving data: "${data}".`;
        }
    }

    class Parser {
        constructor(handlers) {
            this.handlers = handlers;
            this.reset();
        }

        reset() {
            this.protocol = null;
        }

        process(data) {
            try {
                let message;
                if (!this.protocol) {
                    if (data.match(new RegExp("^!!ver:([\\d.]+)$"))) {
                        this.protocol = 6;
                    } else if (message = this._parseMessage(data, ["hello"])) {
                        if (!message.protocols.length) {
                            throw new ProtocolError("no protocols specified in handshake message");
                        } else if (Array.from(message.protocols).includes(PROTOCOL_7)) {
                            this.protocol = 7;
                        } else if (Array.from(message.protocols).includes(PROTOCOL_6)) {
                            this.protocol = 6;
                        } else {
                            throw new ProtocolError("no supported protocols found");
                        }
                    }
                    return this.handlers.connected(this.protocol);
                }
                if (this.protocol === 6) {
                    message = JSON.parse(data);
                    if (!message.length) {
                        throw new ProtocolError("protocol 6 messages must be arrays");
                    }
                    const [command, options] = Array.from(message);
                    if (command !== "refresh") {
                        throw new ProtocolError("unknown protocol 6 command");
                    }
                    return this.handlers.message({
                        command: "reload",
                        path: options.path,
                        liveCSS: options.apply_css_live != null ? options.apply_css_live : true
                    });
                }
                message = this._parseMessage(data, ["reload", "alert"]);
                return this.handlers.message(message);
            } catch (e) {
                if (e instanceof ProtocolError) {
                    return this.handlers.error(e);
                }
                throw e;
            }
        }

        _parseMessage(data, validCommands) {
            let message;
            try {
                message = JSON.parse(data);
            } catch (e) {
                throw new ProtocolError("unparsable JSON", data);
            }
            if (!message.command) {
                throw new ProtocolError('missing "command" key', data);
            }
            if (!validCommands.includes(message.command)) {
                throw new ProtocolError(`invalid command '${message.command}', only valid commands are: ${validCommands.join(", ")})`, data);
            }
            return message;
        }
    }

    // src/connector.js
    var VERSION = "1.0.0";

    class Connector {
        constructor(options, WebSocket, Timer, handlers) {
            this.options = options;
            this.WebSocket = WebSocket;
            this.Timer = Timer;
            this.handlers = handlers;
            const path = this.options.path ? `${this.options.path}` : "livereload";
            const port = this.options.port ? `:${this.options.port}` : "";
            this._uri = `ws${this.options.https ? "s" : ""}://${this.options.host}${port}/${path}`;
            this._nextDelay = this.options.mindelay;
            this._connectionDesired = false;
            this.protocol = 0;
            this.protocolParser = new Parser({
                connected: (protocol) => {
                    this.protocol = protocol;
                    this._handshakeTimeout.stop();
                    this._nextDelay = this.options.mindelay;
                    this._disconnectionReason = "broken";
                    return this.handlers.connected(this.protocol);
                },
                error: (e) => {
                    this.handlers.error(e);
                    return this._closeOnError();
                },
                message: (message) => {
                    return this.handlers.message(message);
                }
            });
            this._handshakeTimeout = new this.Timer(() => {
                if (!this._isSocketConnected()) {
                    return;
                }
                this._disconnectionReason = "handshake-timeout";
                return this.socket.close();
            });
            this._reconnectTimer = new this.Timer(() => {
                if (!this._connectionDesired) {
                    return;
                }
                return this.connect();
            });
            this.connect();
        }

        _isSocketConnected() {
            return this.socket && this.socket.readyState === this.WebSocket.OPEN;
        }

        connect() {
            this._connectionDesired = true;
            if (this._isSocketConnected()) {
                return;
            }
            this._reconnectTimer.stop();
            this._disconnectionReason = "cannot-connect";
            this.protocolParser.reset();
            this.handlers.connecting();
            this.socket = new this.WebSocket(this._uri);
            this.socket.onopen = (e) => this._onopen(e);
            this.socket.onclose = (e) => this._onclose(e);
            this.socket.onmessage = (e) => this._onmessage(e);
            this.socket.onerror = (e) => this._onerror(e);
        }

        disconnect() {
            this._connectionDesired = false;
            this._reconnectTimer.stop();
            if (!this._isSocketConnected()) {
                return;
            }
            this._disconnectionReason = "manual";
            return this.socket.close();
        }

        _scheduleReconnection() {
            if (!this._connectionDesired) {
                return;
            }
            if (!this._reconnectTimer.running) {
                this._reconnectTimer.start(this._nextDelay);
                this._nextDelay = Math.min(this.options.maxdelay, this._nextDelay * 2);
            }
        }

        sendCommand(command) {
            if (!this.protocol) {
                return;
            }
            return this._sendCommand(command);
        }

        _sendCommand(command) {
            return this.socket.send(JSON.stringify(command));
        }

        _closeOnError() {
            this._handshakeTimeout.stop();
            this._disconnectionReason = "error";
            return this.socket.close();
        }

        _onopen(e) {
            this.handlers.socketConnected();
            this._disconnectionReason = "handshake-failed";
            const hello = {
                command: "hello",
                protocols: [PROTOCOL_6, PROTOCOL_7]
            };
            hello.ver = VERSION;
            this._sendCommand(hello);
            return this._handshakeTimeout.start(this.options.handshake_timeout);
        }

        _onclose(e) {
            this.protocol = 0;
            this.handlers.disconnected(this._disconnectionReason, this._nextDelay);
            return this._scheduleReconnection();
        }

        _onerror(e) {
        }

        _onmessage(e) {
            return this.protocolParser.process(e.data);
        }
    }

    // src/timer.js
    class Timer {
        constructor(func) {
            this.func = func;
            this.running = false;
            this.id = null;
            this._handler = () => {
                this.running = false;
                this.id = null;
                return this.func();
            };
        }

        start(timeout) {
            if (this.running) {
                clearTimeout(this.id);
            }
            this.id = setTimeout(this._handler, timeout);
            this.running = true;
        }

        stop() {
            if (this.running) {
                clearTimeout(this.id);
                this.running = false;
                this.id = null;
            }
        }
    }

    Timer.start = (timeout, func) => setTimeout(func, timeout);

    // src/options.js
    class Options {
        constructor() {
            this.https = false;
            this.host = null;
            let port = 35729;
            Object.defineProperty(this, "port", {
                get() {
                    return port;
                },
                set(v) {
                    port = v ? isNaN(v) ? v : +v : "";
                }
            });
            this.mindelay = 1000;
            this.maxdelay = 60000;
            this.handshake_timeout = 5000;
            this.morphHTML = true;
            this.morphShadowDOM = true;
            this.verbose = false;
            this.importCacheWaitPeriod = 200;
        }

        set(name, value) {
            if (typeof value === "undefined") {
                return;
            }
            if (!isNaN(+value)) {
                value = +value;
            }
            if (value === "true") {
                value = true;
            } else if (value === "false") {
                value = false;
            }
            this[name] = value;
        }
    }

    Options.extract = function (document2) {
        const win = document2.defaultView || window;
        if (win && win.LiveMorphOptions) {
            const options = new Options;
            for (const [key, value] of Object.entries(win.LiveMorphOptions)) {
                options.set(key, value);
            }
            return options;
        }
        const scripts = Array.from(document2.getElementsByTagName("script"));
        for (const script of scripts) {
            const host = script.getAttribute("data-livereload-morph-host");
            if (host) {
                const options = new Options;
                options.host = host;
                const port = script.getAttribute("data-livereload-morph-port");
                if (port)
                    options.port = parseInt(port, 10);
                const verbose = script.getAttribute("data-livereload-morph-verbose");
                if (verbose !== null)
                    options.verbose = verbose === "true";
                return options;
            }
        }
        for (const script of scripts) {
            const src = script.src || "";
            if (src.includes("livereload-morph")) {
                const queryIndex = src.indexOf("?");
                if (queryIndex !== -1) {
                    const queryString = src.slice(queryIndex + 1);
                    const params = new URLSearchParams(queryString);
                    const host = params.get("host");
                    if (host) {
                        const options = new Options;
                        options.host = host;
                        for (const [key, value] of params.entries()) {
                            options.set(key, value);
                        }
                        return options;
                    }
                }
            }
        }
        return null;
    };

    // node_modules/idiomorph/dist/idiomorph.esm.js
    var Idiomorph = function () {
        const noOp = () => {
        };
        const defaults = {
            morphStyle: "outerHTML",
            callbacks: {
                beforeNodeAdded: noOp,
                afterNodeAdded: noOp,
                beforeNodeMorphed: noOp,
                afterNodeMorphed: noOp,
                beforeNodeRemoved: noOp,
                afterNodeRemoved: noOp,
                beforeAttributeUpdated: noOp
            },
            head: {
                style: "merge",
                shouldPreserve: (elt) => elt.getAttribute("im-preserve") === "true",
                shouldReAppend: (elt) => elt.getAttribute("im-re-append") === "true",
                shouldRemove: noOp,
                afterHeadMorphed: noOp
            },
            restoreFocus: true
        };

        function morph(oldNode, newContent, config = {}) {
            oldNode = normalizeElement(oldNode);
            const newNode = normalizeParent(newContent);
            const ctx = createMorphContext(oldNode, newNode, config);
            const morphedNodes = saveAndRestoreFocus(ctx, () => {
                return withHeadBlocking(ctx, oldNode, newNode, (ctx2) => {
                    if (ctx2.morphStyle === "innerHTML") {
                        morphChildren(ctx2, oldNode, newNode);
                        return Array.from(oldNode.childNodes);
                    } else {
                        return morphOuterHTML(ctx2, oldNode, newNode);
                    }
                });
            });
            ctx.pantry.remove();
            return morphedNodes;
        }

        function morphOuterHTML(ctx, oldNode, newNode) {
            const oldParent = normalizeParent(oldNode);
            morphChildren(ctx, oldParent, newNode, oldNode, oldNode.nextSibling);
            return Array.from(oldParent.childNodes);
        }

        function saveAndRestoreFocus(ctx, fn) {
            if (!ctx.config.restoreFocus)
                return fn();
            let activeElement = document.activeElement;
            if (!(activeElement instanceof HTMLInputElement || activeElement instanceof HTMLTextAreaElement)) {
                return fn();
            }
            const {id: activeElementId, selectionStart, selectionEnd} = activeElement;
            const results = fn();
            if (activeElementId && activeElementId !== document.activeElement?.getAttribute("id")) {
                activeElement = ctx.target.querySelector(`[id="${activeElementId}"]`);
                activeElement?.focus();
            }
            if (activeElement && !activeElement.selectionEnd && selectionEnd) {
                activeElement.setSelectionRange(selectionStart, selectionEnd);
            }
            return results;
        }

        const morphChildren = function () {
            function morphChildren2(ctx, oldParent, newParent, insertionPoint = null, endPoint = null) {
                if (oldParent instanceof HTMLTemplateElement && newParent instanceof HTMLTemplateElement) {
                    oldParent = oldParent.content;
                    newParent = newParent.content;
                }
                insertionPoint ||= oldParent.firstChild;
                for (const newChild of newParent.childNodes) {
                    if (insertionPoint && insertionPoint != endPoint) {
                        const bestMatch = findBestMatch(ctx, newChild, insertionPoint, endPoint);
                        if (bestMatch) {
                            if (bestMatch !== insertionPoint) {
                                removeNodesBetween(ctx, insertionPoint, bestMatch);
                            }
                            morphNode(bestMatch, newChild, ctx);
                            insertionPoint = bestMatch.nextSibling;
                            continue;
                        }
                    }
                    if (newChild instanceof Element) {
                        const newChildId = newChild.getAttribute("id");
                        if (ctx.persistentIds.has(newChildId)) {
                            const movedChild = moveBeforeById(oldParent, newChildId, insertionPoint, ctx);
                            morphNode(movedChild, newChild, ctx);
                            insertionPoint = movedChild.nextSibling;
                            continue;
                        }
                    }
                    const insertedNode = createNode(oldParent, newChild, insertionPoint, ctx);
                    if (insertedNode) {
                        insertionPoint = insertedNode.nextSibling;
                    }
                }
                while (insertionPoint && insertionPoint != endPoint) {
                    const tempNode = insertionPoint;
                    insertionPoint = insertionPoint.nextSibling;
                    removeNode(ctx, tempNode);
                }
            }

            function createNode(oldParent, newChild, insertionPoint, ctx) {
                if (ctx.callbacks.beforeNodeAdded(newChild) === false)
                    return null;
                if (ctx.idMap.has(newChild)) {
                    const newEmptyChild = document.createElement(newChild.tagName);
                    oldParent.insertBefore(newEmptyChild, insertionPoint);
                    morphNode(newEmptyChild, newChild, ctx);
                    ctx.callbacks.afterNodeAdded(newEmptyChild);
                    return newEmptyChild;
                } else {
                    const newClonedChild = document.importNode(newChild, true);
                    oldParent.insertBefore(newClonedChild, insertionPoint);
                    ctx.callbacks.afterNodeAdded(newClonedChild);
                    return newClonedChild;
                }
            }

            const findBestMatch = function () {
                function findBestMatch2(ctx, node, startPoint, endPoint) {
                    let softMatch = null;
                    let nextSibling = node.nextSibling;
                    let siblingSoftMatchCount = 0;
                    let cursor = startPoint;
                    while (cursor && cursor != endPoint) {
                        if (isSoftMatch(cursor, node)) {
                            if (isIdSetMatch(ctx, cursor, node)) {
                                return cursor;
                            }
                            if (softMatch === null) {
                                if (!ctx.idMap.has(cursor)) {
                                    softMatch = cursor;
                                }
                            }
                        }
                        if (softMatch === null && nextSibling && isSoftMatch(cursor, nextSibling)) {
                            siblingSoftMatchCount++;
                            nextSibling = nextSibling.nextSibling;
                            if (siblingSoftMatchCount >= 2) {
                                softMatch = undefined;
                            }
                        }
                        if (ctx.activeElementAndParents.includes(cursor))
                            break;
                        cursor = cursor.nextSibling;
                    }
                    return softMatch || null;
                }

                function isIdSetMatch(ctx, oldNode, newNode) {
                    let oldSet = ctx.idMap.get(oldNode);
                    let newSet = ctx.idMap.get(newNode);
                    if (!newSet || !oldSet)
                        return false;
                    for (const id of oldSet) {
                        if (newSet.has(id)) {
                            return true;
                        }
                    }
                    return false;
                }

                function isSoftMatch(oldNode, newNode) {
                    const oldElt = oldNode;
                    const newElt = newNode;
                    return oldElt.nodeType === newElt.nodeType && oldElt.tagName === newElt.tagName && (!oldElt.getAttribute?.("id") || oldElt.getAttribute?.("id") === newElt.getAttribute?.("id"));
                }

                return findBestMatch2;
            }();

            function removeNode(ctx, node) {
                if (ctx.idMap.has(node)) {
                    moveBefore(ctx.pantry, node, null);
                } else {
                    if (ctx.callbacks.beforeNodeRemoved(node) === false)
                        return;
                    node.parentNode?.removeChild(node);
                    ctx.callbacks.afterNodeRemoved(node);
                }
            }

            function removeNodesBetween(ctx, startInclusive, endExclusive) {
                let cursor = startInclusive;
                while (cursor && cursor !== endExclusive) {
                    let tempNode = cursor;
                    cursor = cursor.nextSibling;
                    removeNode(ctx, tempNode);
                }
                return cursor;
            }

            function moveBeforeById(parentNode, id, after, ctx) {
                const target = ctx.target.getAttribute?.("id") === id && ctx.target || ctx.target.querySelector(`[id="${id}"]`) || ctx.pantry.querySelector(`[id="${id}"]`);
                removeElementFromAncestorsIdMaps(target, ctx);
                moveBefore(parentNode, target, after);
                return target;
            }

            function removeElementFromAncestorsIdMaps(element, ctx) {
                const id = element.getAttribute("id");
                while (element = element.parentNode) {
                    let idSet = ctx.idMap.get(element);
                    if (idSet) {
                        idSet.delete(id);
                        if (!idSet.size) {
                            ctx.idMap.delete(element);
                        }
                    }
                }
            }

            function moveBefore(parentNode, element, after) {
                if (parentNode.moveBefore) {
                    try {
                        parentNode.moveBefore(element, after);
                    } catch (e) {
                        parentNode.insertBefore(element, after);
                    }
                } else {
                    parentNode.insertBefore(element, after);
                }
            }

            return morphChildren2;
        }();
        const morphNode = function () {
            function morphNode2(oldNode, newContent, ctx) {
                if (ctx.ignoreActive && oldNode === document.activeElement) {
                    return null;
                }
                if (ctx.callbacks.beforeNodeMorphed(oldNode, newContent) === false) {
                    return oldNode;
                }
                if (oldNode instanceof HTMLHeadElement && ctx.head.ignore) {
                } else if (oldNode instanceof HTMLHeadElement && ctx.head.style !== "morph") {
                    handleHeadElement(oldNode, newContent, ctx);
                } else {
                    morphAttributes(oldNode, newContent, ctx);
                    if (!ignoreValueOfActiveElement(oldNode, ctx)) {
                        morphChildren(ctx, oldNode, newContent);
                    }
                }
                ctx.callbacks.afterNodeMorphed(oldNode, newContent);
                return oldNode;
            }

            function morphAttributes(oldNode, newNode, ctx) {
                let type = newNode.nodeType;
                if (type === 1) {
                    const oldElt = oldNode;
                    const newElt = newNode;
                    const oldAttributes = oldElt.attributes;
                    const newAttributes = newElt.attributes;
                    for (const newAttribute of newAttributes) {
                        if (ignoreAttribute(newAttribute.name, oldElt, "update", ctx)) {
                            continue;
                        }
                        if (oldElt.getAttribute(newAttribute.name) !== newAttribute.value) {
                            oldElt.setAttribute(newAttribute.name, newAttribute.value);
                        }
                    }
                    for (let i = oldAttributes.length - 1; 0 <= i; i--) {
                        const oldAttribute = oldAttributes[i];
                        if (!oldAttribute)
                            continue;
                        if (!newElt.hasAttribute(oldAttribute.name)) {
                            if (ignoreAttribute(oldAttribute.name, oldElt, "remove", ctx)) {
                                continue;
                            }
                            oldElt.removeAttribute(oldAttribute.name);
                        }
                    }
                    if (!ignoreValueOfActiveElement(oldElt, ctx)) {
                        syncInputValue(oldElt, newElt, ctx);
                    }
                }
                if (type === 8 || type === 3) {
                    if (oldNode.nodeValue !== newNode.nodeValue) {
                        oldNode.nodeValue = newNode.nodeValue;
                    }
                }
            }

            function syncInputValue(oldElement, newElement, ctx) {
                if (oldElement instanceof HTMLInputElement && newElement instanceof HTMLInputElement && newElement.type !== "file") {
                    let newValue = newElement.value;
                    let oldValue = oldElement.value;
                    syncBooleanAttribute(oldElement, newElement, "checked", ctx);
                    syncBooleanAttribute(oldElement, newElement, "disabled", ctx);
                    if (!newElement.hasAttribute("value")) {
                        if (!ignoreAttribute("value", oldElement, "remove", ctx)) {
                            oldElement.value = "";
                            oldElement.removeAttribute("value");
                        }
                    } else if (oldValue !== newValue) {
                        if (!ignoreAttribute("value", oldElement, "update", ctx)) {
                            oldElement.setAttribute("value", newValue);
                            oldElement.value = newValue;
                        }
                    }
                } else if (oldElement instanceof HTMLOptionElement && newElement instanceof HTMLOptionElement) {
                    syncBooleanAttribute(oldElement, newElement, "selected", ctx);
                } else if (oldElement instanceof HTMLTextAreaElement && newElement instanceof HTMLTextAreaElement) {
                    let newValue = newElement.value;
                    let oldValue = oldElement.value;
                    if (ignoreAttribute("value", oldElement, "update", ctx)) {
                        return;
                    }
                    if (newValue !== oldValue) {
                        oldElement.value = newValue;
                    }
                    if (oldElement.firstChild && oldElement.firstChild.nodeValue !== newValue) {
                        oldElement.firstChild.nodeValue = newValue;
                    }
                }
            }

            function syncBooleanAttribute(oldElement, newElement, attributeName, ctx) {
                const newLiveValue = newElement[attributeName], oldLiveValue = oldElement[attributeName];
                if (newLiveValue !== oldLiveValue) {
                    const ignoreUpdate = ignoreAttribute(attributeName, oldElement, "update", ctx);
                    if (!ignoreUpdate) {
                        oldElement[attributeName] = newElement[attributeName];
                    }
                    if (newLiveValue) {
                        if (!ignoreUpdate) {
                            oldElement.setAttribute(attributeName, "");
                        }
                    } else {
                        if (!ignoreAttribute(attributeName, oldElement, "remove", ctx)) {
                            oldElement.removeAttribute(attributeName);
                        }
                    }
                }
            }

            function ignoreAttribute(attr, element, updateType, ctx) {
                if (attr === "value" && ctx.ignoreActiveValue && element === document.activeElement) {
                    return true;
                }
                return ctx.callbacks.beforeAttributeUpdated(attr, element, updateType) === false;
            }

            function ignoreValueOfActiveElement(possibleActiveElement, ctx) {
                return !!ctx.ignoreActiveValue && possibleActiveElement === document.activeElement && possibleActiveElement !== document.body;
            }

            return morphNode2;
        }();

        function withHeadBlocking(ctx, oldNode, newNode, callback) {
            if (ctx.head.block) {
                const oldHead = oldNode.querySelector("head");
                const newHead = newNode.querySelector("head");
                if (oldHead && newHead) {
                    const promises = handleHeadElement(oldHead, newHead, ctx);
                    return Promise.all(promises).then(() => {
                        const newCtx = Object.assign(ctx, {
                            head: {
                                block: false,
                                ignore: true
                            }
                        });
                        return callback(newCtx);
                    });
                }
            }
            return callback(ctx);
        }

        function handleHeadElement(oldHead, newHead, ctx) {
            let added = [];
            let removed = [];
            let preserved = [];
            let nodesToAppend = [];
            let srcToNewHeadNodes = new Map;
            for (const newHeadChild of newHead.children) {
                srcToNewHeadNodes.set(newHeadChild.outerHTML, newHeadChild);
            }
            for (const currentHeadElt of oldHead.children) {
                let inNewContent = srcToNewHeadNodes.has(currentHeadElt.outerHTML);
                let isReAppended = ctx.head.shouldReAppend(currentHeadElt);
                let isPreserved = ctx.head.shouldPreserve(currentHeadElt);
                if (inNewContent || isPreserved) {
                    if (isReAppended) {
                        removed.push(currentHeadElt);
                    } else {
                        srcToNewHeadNodes.delete(currentHeadElt.outerHTML);
                        preserved.push(currentHeadElt);
                    }
                } else {
                    if (ctx.head.style === "append") {
                        if (isReAppended) {
                            removed.push(currentHeadElt);
                            nodesToAppend.push(currentHeadElt);
                        }
                    } else {
                        if (ctx.head.shouldRemove(currentHeadElt) !== false) {
                            removed.push(currentHeadElt);
                        }
                    }
                }
            }
            nodesToAppend.push(...srcToNewHeadNodes.values());
            let promises = [];
            for (const newNode of nodesToAppend) {
                let newElt = document.createRange().createContextualFragment(newNode.outerHTML).firstChild;
                if (ctx.callbacks.beforeNodeAdded(newElt) !== false) {
                    if ("href" in newElt && newElt.href || "src" in newElt && newElt.src) {
                        let resolve;
                        let promise = new Promise(function (_resolve) {
                            resolve = _resolve;
                        });
                        newElt.addEventListener("load", function () {
                            resolve();
                        });
                        promises.push(promise);
                    }
                    oldHead.appendChild(newElt);
                    ctx.callbacks.afterNodeAdded(newElt);
                    added.push(newElt);
                }
            }
            for (const removedElement of removed) {
                if (ctx.callbacks.beforeNodeRemoved(removedElement) !== false) {
                    oldHead.removeChild(removedElement);
                    ctx.callbacks.afterNodeRemoved(removedElement);
                }
            }
            ctx.head.afterHeadMorphed(oldHead, {
                added,
                kept: preserved,
                removed
            });
            return promises;
        }

        const createMorphContext = function () {
            function createMorphContext2(oldNode, newContent, config) {
                const {persistentIds, idMap} = createIdMaps(oldNode, newContent);
                const mergedConfig = mergeDefaults(config);
                const morphStyle = mergedConfig.morphStyle || "outerHTML";
                if (!["innerHTML", "outerHTML"].includes(morphStyle)) {
                    throw `Do not understand how to morph style ${morphStyle}`;
                }
                return {
                    target: oldNode,
                    newContent,
                    config: mergedConfig,
                    morphStyle,
                    ignoreActive: mergedConfig.ignoreActive,
                    ignoreActiveValue: mergedConfig.ignoreActiveValue,
                    restoreFocus: mergedConfig.restoreFocus,
                    idMap,
                    persistentIds,
                    pantry: createPantry(),
                    activeElementAndParents: createActiveElementAndParents(oldNode),
                    callbacks: mergedConfig.callbacks,
                    head: mergedConfig.head
                };
            }

            function mergeDefaults(config) {
                let finalConfig = Object.assign({}, defaults);
                Object.assign(finalConfig, config);
                finalConfig.callbacks = Object.assign({}, defaults.callbacks, config.callbacks);
                finalConfig.head = Object.assign({}, defaults.head, config.head);
                return finalConfig;
            }

            function createPantry() {
                const pantry = document.createElement("div");
                pantry.hidden = true;
                document.body.insertAdjacentElement("afterend", pantry);
                return pantry;
            }

            function createActiveElementAndParents(oldNode) {
                let activeElementAndParents = [];
                let elt = document.activeElement;
                if (elt?.tagName !== "BODY" && oldNode.contains(elt)) {
                    while (elt) {
                        activeElementAndParents.push(elt);
                        if (elt === oldNode)
                            break;
                        elt = elt.parentElement;
                    }
                }
                return activeElementAndParents;
            }

            function findIdElements(root) {
                let elements = Array.from(root.querySelectorAll("[id]"));
                if (root.getAttribute?.("id")) {
                    elements.push(root);
                }
                return elements;
            }

            function populateIdMapWithTree(idMap, persistentIds, root, elements) {
                for (const elt of elements) {
                    const id = elt.getAttribute("id");
                    if (persistentIds.has(id)) {
                        let current = elt;
                        while (current) {
                            let idSet = idMap.get(current);
                            if (idSet == null) {
                                idSet = new Set;
                                idMap.set(current, idSet);
                            }
                            idSet.add(id);
                            if (current === root)
                                break;
                            current = current.parentElement;
                        }
                    }
                }
            }

            function createIdMaps(oldContent, newContent) {
                const oldIdElements = findIdElements(oldContent);
                const newIdElements = findIdElements(newContent);
                const persistentIds = createPersistentIds(oldIdElements, newIdElements);
                let idMap = new Map;
                populateIdMapWithTree(idMap, persistentIds, oldContent, oldIdElements);
                const newRoot = newContent.__idiomorphRoot || newContent;
                populateIdMapWithTree(idMap, persistentIds, newRoot, newIdElements);
                return {persistentIds, idMap};
            }

            function createPersistentIds(oldIdElements, newIdElements) {
                let duplicateIds = new Set;
                let oldIdTagNameMap = new Map;
                for (const {id, tagName} of oldIdElements) {
                    if (oldIdTagNameMap.has(id)) {
                        duplicateIds.add(id);
                    } else {
                        oldIdTagNameMap.set(id, tagName);
                    }
                }
                let persistentIds = new Set;
                for (const {id, tagName} of newIdElements) {
                    if (persistentIds.has(id)) {
                        duplicateIds.add(id);
                    } else if (oldIdTagNameMap.get(id) === tagName) {
                        persistentIds.add(id);
                    }
                }
                for (const id of duplicateIds) {
                    persistentIds.delete(id);
                }
                return persistentIds;
            }

            return createMorphContext2;
        }();
        const {normalizeElement, normalizeParent} = function () {
            const generatedByIdiomorph = new WeakSet;

            function normalizeElement2(content) {
                if (content instanceof Document) {
                    return content.documentElement;
                } else {
                    return content;
                }
            }

            function normalizeParent2(newContent) {
                if (newContent == null) {
                    return document.createElement("div");
                } else if (typeof newContent === "string") {
                    return normalizeParent2(parseContent(newContent));
                } else if (generatedByIdiomorph.has(newContent)) {
                    return newContent;
                } else if (newContent instanceof Node) {
                    if (newContent.parentNode) {
                        return new SlicedParentNode(newContent);
                    } else {
                        const dummyParent = document.createElement("div");
                        dummyParent.append(newContent);
                        return dummyParent;
                    }
                } else {
                    const dummyParent = document.createElement("div");
                    for (const elt of [...newContent]) {
                        dummyParent.append(elt);
                    }
                    return dummyParent;
                }
            }

            class SlicedParentNode {
                constructor(node) {
                    this.originalNode = node;
                    this.realParentNode = node.parentNode;
                    this.previousSibling = node.previousSibling;
                    this.nextSibling = node.nextSibling;
                }

                get childNodes() {
                    const nodes = [];
                    let cursor = this.previousSibling ? this.previousSibling.nextSibling : this.realParentNode.firstChild;
                    while (cursor && cursor != this.nextSibling) {
                        nodes.push(cursor);
                        cursor = cursor.nextSibling;
                    }
                    return nodes;
                }

                querySelectorAll(selector) {
                    return this.childNodes.reduce((results, node) => {
                        if (node instanceof Element) {
                            if (node.matches(selector))
                                results.push(node);
                            const nodeList = node.querySelectorAll(selector);
                            for (let i = 0; i < nodeList.length; i++) {
                                results.push(nodeList[i]);
                            }
                        }
                        return results;
                    }, []);
                }

                insertBefore(node, referenceNode) {
                    return this.realParentNode.insertBefore(node, referenceNode);
                }

                moveBefore(node, referenceNode) {
                    return this.realParentNode.moveBefore(node, referenceNode);
                }

                get __idiomorphRoot() {
                    return this.originalNode;
                }
            }

            function parseContent(newContent) {
                let parser = new DOMParser;
                let contentWithSvgsRemoved = newContent.replace(/<svg(\s[^>]*>|>)([\s\S]*?)<\/svg>/gim, "");
                if (contentWithSvgsRemoved.match(/<\/html>/) || contentWithSvgsRemoved.match(/<\/head>/) || contentWithSvgsRemoved.match(/<\/body>/)) {
                    let content = parser.parseFromString(newContent, "text/html");
                    if (contentWithSvgsRemoved.match(/<\/html>/)) {
                        generatedByIdiomorph.add(content);
                        return content;
                    } else {
                        let htmlElement = content.firstChild;
                        if (htmlElement) {
                            generatedByIdiomorph.add(htmlElement);
                        }
                        return htmlElement;
                    }
                } else {
                    let responseDoc = parser.parseFromString("<body><template>" + newContent + "</template></body>", "text/html");
                    let content = responseDoc.body.querySelector("template").content;
                    generatedByIdiomorph.add(content);
                    return content;
                }
            }

            return {normalizeElement: normalizeElement2, normalizeParent: normalizeParent2};
        }();
        return {
            morph,
            defaults
        };
    }();

    // src/utils.js
    function splitUrl(url) {
        let hash = "";
        let params = "";
        let index = url.indexOf("#");
        if (index >= 0) {
            hash = url.slice(index);
            url = url.slice(0, index);
        }
        const comboSign = url.indexOf("??");
        if (comboSign >= 0) {
            if (comboSign + 1 !== url.lastIndexOf("?")) {
                index = url.lastIndexOf("?");
            }
        } else {
            index = url.indexOf("?");
        }
        if (index >= 0) {
            params = url.slice(index);
            url = url.slice(0, index);
        }
        return {url, params, hash};
    }

    function pathFromUrl(url) {
        if (!url) {
            return "";
        }
        let path;
        ({url} = splitUrl(url));
        if (url.indexOf("file://") === 0) {
            path = url.replace(new RegExp("^file://(localhost)?"), "");
        } else {
            path = url.replace(new RegExp("^([^:]+:)?//([^:/]+)(:\\d*)?/"), "/");
        }
        return decodeURIComponent(path);
    }

    function numberOfMatchingSegments(left, right) {
        left = left.replace(/^\/+/, "").toLowerCase();
        right = right.replace(/^\/+/, "").toLowerCase();
        if (left === right) {
            return 1e4;
        }
        const comps1 = left.split(/\/|\\/).reverse();
        const comps2 = right.split(/\/|\\/).reverse();
        const len = Math.min(comps1.length, comps2.length);
        let eqCount = 0;
        while (eqCount < len && comps1[eqCount] === comps2[eqCount]) {
            ++eqCount;
        }
        return eqCount;
    }

    function pickBestMatch(path, objects, pathFunc = (s) => s) {
        let bestMatch = {score: 0};
        for (const object of objects) {
            const score = numberOfMatchingSegments(path, pathFunc(object));
            if (score > bestMatch.score) {
                bestMatch = {object, score};
            }
        }
        if (bestMatch.score === 0) {
            return null;
        }
        return bestMatch;
    }

    function generateCacheBustUrl(url) {
        const {url: cleanUrl, params, hash} = splitUrl(url);
        const expando = `livereload=${Date.now()}`;
        if (!params) {
            return `${cleanUrl}?${expando}${hash}`;
        }
        if (params.includes("livereload=")) {
            const newParams = params.replace(/([?&])livereload=\d+/, `$1${expando}`);
            return `${cleanUrl}${newParams}${hash}`;
        }
        return `${cleanUrl}${params}&${expando}${hash}`;
    }

    function waitForStylesheetLoad(linkElement, timeout = 15000) {
        return new Promise((resolve) => {
            let resolved = false;
            const finish = () => {
                if (resolved)
                    return;
                resolved = true;
                resolve();
            };
            linkElement.onload = () => {
                finish();
            };
            const pollInterval = 50;
            const poll = () => {
                if (resolved)
                    return;
                if (linkElement.sheet) {
                    finish();
                    return;
                }
                setTimeout(poll, pollInterval);
            };
            setTimeout(poll, pollInterval);
            setTimeout(finish, timeout);
        });
    }

    // src/morpher.js
    class Morpher {
        constructor(window2, console2, Timer2, importCacheWaitPeriod = 200) {
            this.window = window2;
            this.console = console2;
            this.Timer = Timer2;
            this.document = window2.document;
            this.importCacheWaitPeriod = importCacheWaitPeriod;
            this.preserveStateCallback = (attributeName, node) => {
                if (node.tagName === "HTML" && attributeName === "class") return false;
                if (node.tagName === "BODY" && attributeName === "class" && node.classList.contains("ready")) return false;
                if (node.tagName === "WA-DIALOG" && attributeName === "open") return false;
                if (node.tagName === "WA-POPOVER" && attributeName === "open") return false;
                if (node.tagName === "INPUT" || node.tagName === "TEXTAREA" || node.tagName === "SELECT") {
                    if (attributeName === "value" || attributeName === "checked") {
                        return false;
                    }
                }
                if (node.tagName === "DETAILS" && attributeName === "open") {
                    return false;
                }
                return true;
            };
        }

        reload(path, options = {}) {
            const isCSSFile = path.match(/\.css(?:\.map)?$/i);
            const isImageFile = path.match(/\.(jpe?g|png|gif|svg|webp|ico)$/i);
            const isJSFile = path.match(/\.m?js$/i);
            if (isCSSFile && options.liveCSS) {
                return this.reloadStylesheet(path, options);
            }
            if (isImageFile && options.liveImg) {
                return this.reloadImages(path);
            }
            if (isJSFile) {
                return this.reloadPage();
            }
            if (options.morphHTML) {
                return this.morphHTML(path, options);
            }
            this.reloadPage();
        }

        async morphHTML(path, options = {}) {
            try {
                const response = await fetch(this.window.location.href, {
                    cache: "no-cache",
                    headers: {"X-Live-Morph": "true"}
                });
                if (!response.ok) {
                    throw new Error(`Fetch failed: ${response.status} ${response.statusText}`);
                }
                let html = await response.text();
                // html = html.replace(/<!DOCTYPE[^>]*>/i, "").trim();
                const parser = new DOMParser;
                const newDoc = parser.parseFromString(html, "text/html");
                Idiomorph.morph(this.document.documentElement, newDoc.documentElement, {
                    head: {
                        style: "merge",
                        shouldPreserve: (headEl) => {
                            return true;
                        },
                        shouldRemove: (headEl) => {
                            return false;
                        }
                    },
                    callbacks: {
                        beforeNodeMorphed: (oldNode, newNode) => {
                            if (oldNode.tagName === "IFRAME" && newNode.tagName === "IFRAME") {
                                const newSrcdoc = newNode.getAttribute("srcdoc");
                                if (newSrcdoc !== null) {
                                    this.morphIframeSrcdoc(oldNode, newSrcdoc, options);
                                    return false;
                                }
                            }
                            return true;
                        },
                        afterNodeMorphed: (oldNode, newNode) => {
                            if (options.morphShadowDOM === false)
                                return;
                            if (oldNode.children) {
                                for (const child of oldNode.children) {
                                    if (child.tagName === "TEMPLATE" && (child.hasAttribute("shadowrootmode") || child.hasAttribute("shadowroot"))) {
                                        const shadowOptions = {
                                            mode: child.getAttribute("shadowrootmode") || child.getAttribute("shadowroot") || "open"
                                        };
                                        if (child.hasAttribute("shadowrootdelegatesfocus")) {
                                            shadowOptions.delegatesFocus = true;
                                        }
                                        if (child.hasAttribute("shadowrootclonable")) {
                                            shadowOptions.clonable = true;
                                        }
                                        if (child.hasAttribute("shadowrootserializable")) {
                                            shadowOptions.serializable = true;
                                        }
                                        let shadow = oldNode.shadowRoot;
                                        if (!shadow) {
                                            shadow = oldNode.attachShadow(shadowOptions);
                                        }
                                        shadow.innerHTML = "";
                                        shadow.appendChild(child.content.cloneNode(true));
                                        child.remove();
                                        break;
                                    }
                                }
                            }
                        },
                        beforeAttributeUpdated: this.preserveStateCallback
                    }
                });
                this.console.log("HTML morphed successfully");
            } catch (error) {
                this.console.error(`Morph failed: ${error.message}`);
                if (options.fallbackToReload !== false) {
                    this.console.log("Falling back to full page reload");
                    this.reloadPage();
                }
            }
        }

        morphIframeSrcdoc(iframe, newSrcdoc, options = {}) {
            try {
                const iframeDoc = iframe.contentDocument;
                if (!iframeDoc || !iframeDoc.body) {
                    this.console.log("Cannot access iframe contentDocument, falling back to srcdoc replacement");
                    iframe.setAttribute("srcdoc", newSrcdoc);
                    return;
                }
                const parser = new DOMParser;
                const newDoc = parser.parseFromString(newSrcdoc, "text/html");
                const morphOptions = {
                    morphStyle: "innerHTML",
                    callbacks: {
                        beforeAttributeUpdated: this.preserveStateCallback
                    }
                };
                if (newDoc.head) {
                    if (iframeDoc.head) {
                        Idiomorph.morph(iframeDoc.head, newDoc.head.innerHTML, morphOptions);
                    } else {
                        const head = iframeDoc.createElement("head");
                        head.innerHTML = newDoc.head.innerHTML;
                        iframeDoc.documentElement.insertBefore(head, iframeDoc.body);
                    }
                }
                if (newDoc.body) {
                    Idiomorph.morph(iframeDoc.body, newDoc.body.innerHTML, morphOptions);
                }
                this.console.log("iframe srcdoc morphed successfully");
            } catch (error) {
                this.console.error(`iframe srcdoc morph failed, so we fall back. msg: ${error.message}`);
                iframe.setAttribute("srcdoc", newSrcdoc);
            }
        }

        async reloadStylesheet(path, options = {}) {
            try {
                const links = Array.from(this.document.getElementsByTagName("link")).filter((link) => link.rel && link.rel.match(/^stylesheet$/i) && !link.__LiveReload_pendingRemoval);
                const imported = [];
                for (const style of Array.from(this.document.getElementsByTagName("style"))) {
                    if (style.sheet) {
                        this.collectImportedStylesheets(style, style.sheet, imported);
                    }
                }
                for (const link of links) {
                    if (link.sheet) {
                        this.collectImportedStylesheets(link, link.sheet, imported);
                    }
                }
                if (this.window.StyleFix && this.document.querySelectorAll) {
                    for (const style of Array.from(this.document.querySelectorAll("style[data-href]"))) {
                        links.push(style);
                    }
                }
                this.console.log(`CSS reload: found ${links.length} LINKed stylesheets, ${imported.length} @imported stylesheets`);
                const match = pickBestMatch(path, links.concat(imported), (item) => pathFromUrl(item.href || this.linkHref(item)));
                if (!match) {
                    if (options.reloadMissingCSS !== false) {
                        this.console.log(`CSS reload: no match found for '${path}', reloading all stylesheets`);
                        for (const link of links) {
                            await this.reattachStylesheetLink(link);
                        }
                    } else {
                        this.console.log(`CSS reload: no match found for '${path}', skipping (reloadMissingCSS=false)`);
                    }
                    return;
                }
                if (match.object.rule) {
                    this.console.log(`CSS reload: reloading @imported stylesheet: ${match.object.href}`);
                    await this.reattachImportedRule(match.object);
                } else {
                    this.console.log(`CSS reload: reloading stylesheet: ${this.linkHref(match.object)}`);
                    await this.reattachStylesheetLink(match.object);
                }
            } catch (error) {
                this.console.error(`Stylesheet reload failed: ${error.message}`);
                this.console.error("Stack:", error.stack);
            }
        }

        async reattachStylesheetLink(link) {
            if (link.__LiveReload_pendingRemoval) {
                return;
            }
            link.__LiveReload_pendingRemoval = true;
            let clone;
            if (link.tagName === "STYLE") {
                clone = this.document.createElement("link");
                clone.rel = "stylesheet";
                clone.media = link.media;
                clone.disabled = link.disabled;
            } else {
                clone = link.cloneNode(false);
            }
            clone.href = generateCacheBustUrl(this.linkHref(link));
            const parent = link.parentNode;
            if (parent.lastChild === link) {
                parent.appendChild(clone);
            } else {
                parent.insertBefore(clone, link.nextSibling);
            }
            await waitForStylesheetLoad(clone);
            const additionalWait = /AppleWebKit/.test(this.window.navigator.userAgent) ? 5 : 200;
            await new Promise((resolve) => this.Timer.start(additionalWait, resolve));
            if (link.parentNode) {
                link.parentNode.removeChild(link);
            }
            if (this.window.StyleFix) {
                this.window.StyleFix.link(clone);
            }
        }

        reloadPage() {
            this.window.location.reload();
        }

        reloadImages(path) {
            for (const img of Array.from(this.document.images)) {
                if (this.pathsMatch(path, pathFromUrl(img.src))) {
                    img.src = generateCacheBustUrl(img.src);
                }
            }
            const bgSelectors = ["background", "border"];
            const bgStyleNames = ["backgroundImage", "borderImage", "webkitBorderImage", "MozBorderImage"];
            for (const selector of bgSelectors) {
                for (const el of Array.from(this.document.querySelectorAll(`[style*=${selector}]`))) {
                    this.reloadStyleImages(el.style, bgStyleNames, path);
                }
            }
            for (const sheet of Array.from(this.document.styleSheets)) {
                this.reloadStylesheetImages(sheet, path);
            }
            this.console.log(`Image reload: ${path}`);
        }

        reloadStylesheetImages(styleSheet, path) {
            let rules;
            try {
                rules = (styleSheet || {}).cssRules;
            } catch (e) {
                return;
            }
            if (!rules)
                return;
            const bgStyleNames = ["backgroundImage", "borderImage", "webkitBorderImage", "MozBorderImage"];
            for (const rule of Array.from(rules)) {
                switch (rule.type) {
                    case CSSRule.IMPORT_RULE:
                        this.reloadStylesheetImages(rule.styleSheet, path);
                        break;
                    case CSSRule.STYLE_RULE:
                        this.reloadStyleImages(rule.style, bgStyleNames, path);
                        break;
                    case CSSRule.MEDIA_RULE:
                        this.reloadStylesheetImages(rule, path);
                        break;
                }
            }
        }

        reloadStyleImages(style, styleNames, path) {
            for (const styleName of styleNames) {
                const value = style[styleName];
                if (typeof value === "string") {
                    const newValue = value.replace(/\burl\s*\(([^)]*)\)/g, (match, src) => {
                        const cleanSrc = src.replace(/^['"]|['"]$/g, "");
                        if (this.pathsMatch(path, pathFromUrl(cleanSrc))) {
                            return `url(${generateCacheBustUrl(cleanSrc)})`;
                        }
                        return match;
                    });
                    if (newValue !== value) {
                        style[styleName] = newValue;
                    }
                }
            }
        }

        pathsMatch(path1, path2) {
            const segs1 = path1.replace(/^\//, "").split("/").reverse();
            const segs2 = path2.replace(/^\//, "").split("/").reverse();
            const len = Math.min(segs1.length, segs2.length);
            for (let i = 0; i < len; i++) {
                if (segs1[i] !== segs2[i])
                    return false;
            }
            return len > 0;
        }

        linkHref(link) {
            return link.href || link.getAttribute && link.getAttribute("data-href");
        }

        collectImportedStylesheets(link, styleSheet, result) {
            let rules;
            try {
                rules = (styleSheet || {}).cssRules;
            } catch (e) {
                return;
            }
            if (rules && rules.length) {
                for (let index = 0; index < rules.length; index++) {
                    const rule = rules[index];
                    switch (rule.type) {
                        case CSSRule.CHARSET_RULE:
                            continue;
                        case CSSRule.IMPORT_RULE:
                            result.push({link, rule, index, href: rule.href});
                            this.collectImportedStylesheets(link, rule.styleSheet, result);
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        async reattachImportedRule({rule, index, link}) {
            const parent = rule.parentStyleSheet;
            const href = generateCacheBustUrl(rule.href);
            let media = "";
            try {
                media = rule.media.length ? [].join.call(rule.media, ", ") : "";
            } catch (e) {
                if (e.name !== "SecurityError") {
                    this.console.error(`Unexpected error accessing @import media: ${e.name}: ${e.message}`);
                }
            }
            const newRule = `@import url("${href}") ${media};`;
            rule.__LiveReload_newHref = href;
            if (this.importCacheWaitPeriod > 0) {
                const tempLink = this.document.createElement("link");
                tempLink.rel = "stylesheet";
                tempLink.href = href;
                tempLink.__LiveReload_pendingRemoval = true;
                if (link.parentNode) {
                    link.parentNode.insertBefore(tempLink, link);
                }
                await new Promise((resolve) => this.Timer.start(this.importCacheWaitPeriod, resolve));
                if (tempLink.parentNode) {
                    tempLink.parentNode.removeChild(tempLink);
                }
                if (rule.__LiveReload_newHref !== href) {
                    return;
                }
            }
            parent.insertRule(newRule, index);
            parent.deleteRule(index + 1);
            if (this.importCacheWaitPeriod > 0) {
                const updatedRule = parent.cssRules[index];
                updatedRule.__LiveReload_newHref = href;
                await new Promise((resolve) => this.Timer.start(this.importCacheWaitPeriod, resolve));
                if (updatedRule.__LiveReload_newHref !== href) {
                    return;
                }
                parent.insertRule(newRule, index);
                parent.deleteRule(index + 1);
            }
        }
    }

    // src/live-morph.js
    class LiveMorph {
        constructor(window2) {
            this.window = window2;
            this.listeners = {};
            if (!(this.WebSocket = this.window.WebSocket || this.window.MozWebSocket)) {
                console.error("[LiveMorph] Disabled because the browser does not support WebSockets");
                return;
            }
            this.options = Options.extract(this.window.document);
            if (!this.options) {
                console.error("[LiveMorph] Disabled - no configuration found");
                console.error('[LiveMorph] Set window.LiveMorphOptions = { host: "localhost", port: 35729 }');
                return;
            }
            console.log("[LiveMorph] Options loaded:", JSON.stringify({
                host: this.options.host,
                port: this.options.port,
                morphHTML: this.options.morphHTML,
                verbose: this.options.verbose
            }));
            this.console = this._setupConsole();
            this.morpher = new Morpher(this.window, this.console, Timer, this.options.importCacheWaitPeriod);
            this.connector = new Connector(this.options, this.WebSocket, Timer, {
                connecting: () => {
                },
                socketConnected: () => {
                },
                connected: (protocol) => {
                    if (typeof this.listeners.connect === "function") {
                        this.listeners.connect();
                    }
                    const {host} = this.options;
                    const port = this.options.port ? `:${this.options.port}` : "";
                    this.log(`Connected to ${host}${port} (protocol v${protocol})`);
                    return this.sendInfo();
                },
                error: (e) => {
                    if (e instanceof ProtocolError) {
                        return console.log(`[LiveMorph] ${e.message}`);
                    } else {
                        return console.log(`[LiveMorph] Internal error: ${e.message}`);
                    }
                },
                disconnected: (reason, nextDelay) => {
                    if (typeof this.listeners.disconnect === "function") {
                        this.listeners.disconnect();
                    }
                    const {host} = this.options;
                    const port = this.options.port ? `:${this.options.port}` : "";
                    const delaySec = (nextDelay / 1000).toFixed(0);
                    switch (reason) {
                        case "cannot-connect":
                            return this.log(`Cannot connect to ${host}${port}, will retry in ${delaySec} sec`);
                        case "broken":
                            return this.log(`Disconnected from ${host}${port}, reconnecting in ${delaySec} sec`);
                        case "handshake-timeout":
                            return this.log(`Cannot connect to ${host}${port} (handshake timeout), will retry in ${delaySec} sec`);
                        case "handshake-failed":
                            return this.log(`Cannot connect to ${host}${port} (handshake failed), will retry in ${delaySec} sec`);
                        case "manual":
                        case "error":
                        default:
                            return this.log(`Disconnected from ${host}${port} (${reason}), reconnecting in ${delaySec} sec`);
                    }
                },
                message: (message) => {
                    switch (message.command) {
                        case "reload":
                            return this.performReload(message);
                        case "alert":
                            return this.performAlert(message);
                    }
                }
            });
            this.initialized = true;
        }

        _setupConsole() {
            const hasConsole = this.window.console && this.window.console.log && this.window.console.error;
            if (!hasConsole) {
                return {
                    log() {
                    }, error() {
                    }
                };
            }
            if (this.options.verbose) {
                return this.window.console;
            }
            return {
                log() {
                },
                error: this.window.console.error.bind(this.window.console)
            };
        }

        on(eventName, handler) {
            this.listeners[eventName] = handler;
        }

        log(message) {
            return this.console.log(`[LiveMorph] ${message}`);
        }

        performReload(message) {
            this.log(`Received reload request for: ${message.path}`);
            const options = {
                liveCSS: message.liveCSS != null ? message.liveCSS : true,
                liveImg: message.liveImg != null ? message.liveImg : true,
                reloadMissingCSS: message.reloadMissingCSS != null ? message.reloadMissingCSS : true,
                morphHTML: this.options.morphHTML,
                morphShadowDOM: this.options.morphShadowDOM
            };
            this.log(`Reload options: ${JSON.stringify(options)}`);
            return this.morpher.reload(message.path, options);
        }

        performAlert(message) {
            return alert(message.message);
        }

        sendInfo() {
            if (!this.initialized) {
                return;
            }
            if (!(this.connector.protocol >= 7)) {
                return;
            }
            this.connector.sendCommand({
                command: "info",
                plugins: {},
                url: this.window.location.href
            });
        }

        shutDown() {
            if (!this.initialized) {
                return;
            }
            this.connector.disconnect();
            this.log("Disconnected");
            if (typeof this.listeners.shutdown === "function") {
                this.listeners.shutdown();
            }
        }
    }

    // src/index.js
    var liveMorph = new LiveMorph(window);
    window.LiveMorph = liveMorph;
    if (typeof document !== "undefined") {
        document.addEventListener("LiveMorphShutDown", () => {
            liveMorph.shutDown();
        });
        liveMorph.on("connect", () => {
            const event = new CustomEvent("LiveMorphConnect");
            document.dispatchEvent(event);
        });
        liveMorph.on("disconnect", () => {
            const event = new CustomEvent("LiveMorphDisconnect");
            document.dispatchEvent(event);
        });
    }
    var src_default = liveMorph;
})();
