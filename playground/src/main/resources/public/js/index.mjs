// This is POC for a new FOUCE prevention mechanism which uses MutationObservers
// to detect when HTMX fragments are added to the DOM.
(() => {
        window.LiveMorphOptions = { host: "localhost", port: 35729, verbose: true }

        htmx.config.transitions = false;

        const isWa = el => el.tagName && el.tagName.startsWith('WA-');
        const collectWaTags = (root) => {
            const set = new Set();
            root.querySelectorAll('*').forEach(el => {
                if (isWa(el)) set.add(el.tagName.toLowerCase());
            });
            return set;
        };
        const getTimeout = (el) => parseInt(el?.getAttribute?.('data-wc-timeout') || '4000', 10);

        const waitAllDefined = (tagSet) => Promise.all(Array.from(tagSet).map(async t => {
            await customElements.whenDefined(t);
        }));
        const withTimeout = (p, ms) =>
            Promise.race([p.catch(() => {
            }), new Promise(res => setTimeout(res, ms))]);

        const flipReadyNextFrame = (el) => {
            if (!el) return;
            el.classList.remove('ready');
            void el.offsetWidth;               // force reflow to commit opacity:0
            requestAnimationFrame(() => el.classList.add('ready'));
        };

        const scanAndReady = async (root, addClassTo = root) => {
            const tags = collectWaTags(root);
            await withTimeout(waitAllDefined(tags), getTimeout(addClassTo));
            flipReadyNextFrame(addClassTo);
        };

        const init = () => {
            // BODY: wait (with timeout) for all wa-* on the page, then ready the body
            scanAndReady(document, document.body);

            // Watch for newly added HTMX fragments: id starts with "hx-fragment-"
            const mo = new MutationObserver(muts => {
                for (const m of muts) {
                    for (const n of m.addedNodes) {
                        if (n.nodeType !== 1) continue;
                        const el = n;
                        if (el.id && el.id.startsWith('hx-fragment-')) {
                            scanAndReady(el, el);
                        } else if (el.querySelector) {
                            el.querySelectorAll('[id^="hx-fragment-"]').forEach(f => scanAndReady(f, f));
                        }
                    }
                }
            });
            mo.observe(document.body, {childList: true, subtree: true});
        };

        if (document.readyState === 'loading') {
            document.addEventListener('DOMContentLoaded', init, {once: true});
        } else {
            init();
        }
    }
)
();
