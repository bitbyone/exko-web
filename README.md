# exko-web

> [!WARNING]
> This project is **highly experimental**. The API is actively evolving and may change at any time without notice. This is the reason the library is not published to Maven Central — use it as a composite build or local dependency only.

An opinionated set of Kotlin glue libraries that combine [kotlinx.html](https://github.com/Kotlin/kotlinx.html), [HTMX](https://htmx.org/), [WebAwesome](https://www.webawesome.com/), and [Spring Boot](https://spring.io/projects/spring-boot) into a cohesive, server-driven environment for building web applications — entirely in Kotlin.

### Philosophy

**Do as much as possible with a single language.**

Exko leverages Kotlin's type system and DSL capabilities to let you write HTML, CSS, and component logic in one place — the same language your backend already runs on. Plain JavaScript and CSS are used only where truly necessary: small, purpose-built scripts for client-side interactivity via [Stimulus](https://stimulus.hotwired.dev/), and raw CSS only for things that can't be expressed through scoped property delegates.

Everything else is server-driven. No SPA, no build pipeline for the frontend, no separate frontend project.

This approach is a natural fit for **backend developers whose daily driver is Kotlin** and who want to keep the UI layer tightly coupled with business logic in a single project — ideal for internal tools, backoffice applications, and admin dashboards where the UI is added value on top of existing backend logic, not a standalone product.

```kotlin
@UI
fun Component.ProjectCard(project: Project) {
    Card {
        h3 { +project.name }
        p { +project.description }
        Button(variant = Variant.accent) {
            hx {
                get("/projects/${project.id}")
                target("#content")
                swapInnerHTML()
            }
            +"Open"
        }
    }
}
```

## Modules

### Core (Kotlin)

| Module | Description |
|--------|-------------|
| **kotlin-html** | Extensions for [kotlinx.html](https://github.com/Kotlin/kotlinx.html) — `@UI` annotation, `Component`/`Children` type aliases, `view()` rendering, web component support |
| **kotlin-htmx** | Type-safe DSL for HTMX attributes (`hx { get(...); target(...); swap { innerHTML() } }`) |
| **kotlin-webawesome** | Full Kotlin DSL for [WebAwesome 3.x](https://www.webawesome.com/) — 30+ components (Button, Card, Dialog, Tree, Input, Avatar, Toast, Tabs, …) with typed props |
| **styled** | CSS-in-Kotlin via property delegates — scoped class names, content-hashed bundles |
| **styled-ksp** | KSP compile-time processor — discovers `@Css` objects and generates ServiceLoader registrars |
| **kotlin-stimulus** | DSL for defining [Stimulus](https://stimulus.hotwired.dev/) controllers in Kotlin |
| **webawesome-blocks** | Pre-built composite components (e.g. `AvatarGroup`) combining WebAwesome + Styled |

### Spring Integration

| Module | Description |
|--------|-------------|
| **spring-htmx** | Auto-configuration for HTMX — `Render` return type handling, fragment rendering, HTMX request context, error layouts |
| **spring-styled** | Serves bundled CSS at `/__scoped-css/` with immutable cache headers + HotSwap Agent plugin for live CSS reload |
| **spring-stimulus** | Bundles Stimulus controllers into hashed JS files, serves at `/__stimulus/` |
| **spring-hotswap-agent** | File watcher + Spring DevTools LiveReload integration for instant browser refresh |

```
kotlin-html
    └── kotlin-htmx
            ├── styled ──── styled-ksp
            ├── kotlin-stimulus
            └── kotlin-webawesome
                    └── webawesome-blocks

spring-htmx
    ├── spring-styled
    ├── spring-stimulus
    └── spring-hotswap-agent
```

## Quick Start

### 1. Project setup

Add exko-web as a [composite build](https://docs.gradle.org/current/userguide/composite_builds.html) in your `settings.gradle.kts`:

```kotlin
includeBuild("lib/exko-web")
```

Add dependencies in `build.gradle.kts`:

```kotlin
plugins {
    id("com.google.devtools.ksp")
}

dependencies {
    ksp("io.exko:styled-ksp")
    
    api("io.exko:kotlin-webawesome")
    api("io.exko:styled")
    implementation("io.exko:spring-htmx")
    implementation("io.exko:spring-styled")
    implementation("io.exko:spring-stimulus")
    implementation("io.exko:spring-hotswap-agent")
}
```

Register styled objects at startup:

```kotlin
@SpringBootApplication
class MyApplication {
    @EventListener
    fun init(e: ApplicationStartedEvent) {
        registerStyled()
    }
}
```

### 2. Define a page

```kotlin
@UI
fun Component.UsersPage(users: List<User>) {
    h1 { +"Users" }
    div(classes = PageStyles.grid) {
        users.forEach { user ->
            Card {
                div("wa-flank wa-gap-s") {
                    Avatar(initials = user.initials)
                    span { +user.name }
                }
                p(classes = "wa-color-text-quiet") { +user.email }
            }
        }
    }
}

@Css
object PageStyles : Styled() {
    val grid by css {
        """
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
        gap: var(--wa-space-m);
        """
    }
}
```

### 3. Wire up a controller

```kotlin
@Controller
@RequestMapping("/users")
class UsersController(private val userService: UserService) {

    @GetMapping
    fun index(): Render {
        val users = userService.findAll()
        return view(
            page = fragment { UsersPage(users) },
            layout = ::MainLayout,
        )
    }
}
```

## Usage Examples

### HTMX — Dynamic content loading

```kotlin
@UI
fun Component.SearchForm() {
    Input(type = InputType.search, label = "Search users") {
        hx {
            get("/api/users/search")
            trigger { event("input"); changed(); delay("300ms") }
            target("#search-results")
            swapInnerHTML()
        }
    }
    div { id = "search-results" }
}
```

### HTMX — Form submission with dialog

```kotlin
@UI
fun Component.AddUserDialog() {
    Dialog(id = "add-user-dialog", label = "Add User") {
        form {
            hx {
                post("/api/users")
                target("#user-list")
                swapInnerHTML()
            }
            Input(type = InputType.text, label = "Name", name = "name", required = true)
            Input(type = InputType.email, label = "Email", name = "email", required = true)
            Button(variant = Variant.accent, type = ButtonType.submit) { +"Save" }
        }
    }
}
```

### HTMX — Tree with lazy loading

```kotlin
@UI
fun Component.FileExplorer() {
    Tree(id = "file-tree") {
        hx {
            get("/api/files/tree")
            trigger { event("load") }
            swapInnerHTML()
        }
    }
}
```

### Styled — Scoped CSS-in-Kotlin

```kotlin
@Css
object ProfileStyles : Styled() {

    val card by css {
        """
        max-width: 400px;
        border: 1px solid var(--wa-color-border-default);
        border-radius: var(--wa-radius-m);
        padding: var(--wa-space-l);
        """
    }

    val avatar by css {
        """
        --size: 4rem;
        margin-bottom: var(--wa-space-m);
        """
    }
}

// Usage — class names are auto-scoped (e.g. "card-a1b2c3")
@UI
fun Component.ProfileCard(user: User) {
    div(classes = ProfileStyles.card) {
        Avatar(initials = user.initials, classes = ProfileStyles.avatar)
        h2 { +user.name }
    }
}
```

### Stimulus — Client-side interactivity

Define controllers as Spring beans:

```kotlin
@Component
object CopyButtonController : StimulusController("copy-button", {
    """
    import {Controller} from "@hotwired/stimulus"

    export default class extends Controller {
        static targets = ["source"]

        copy() {
            navigator.clipboard.writeText(this.sourceTarget.value)
        }
    }
    """
})
```

Use in your HTML with the `stimulus()` DSL:

```kotlin
@UI
fun Component.CopyField(value: String) {
    div {
        stimulus(CopyButtonController) { registerController() }
        Input(type = InputType.text, value = value, readonly = true) {
            stimulus(CopyButtonController) { target("source") }
        }
        Button(variant = Variant.neutral) {
            stimulus(CopyButtonController) { action("copy") }
            Icon(name = "clipboard")
            +"Copy"
        }
    }
}
```

### WebAwesome — Component composition

```kotlin
@UI
fun Component.UserMenu(user: User) {
    Dropdown {
        Avatar(
            initials = user.initials,
            slot = "trigger",
        )
        DropdownItem {
            Icon(name = "user", slot = "prefix")
            +"Profile"
        }
        Divider()
        DropdownItem(variant = DropdownVariant.danger) {
            hx {
                post("/logout")
                swapNone()
                on().after().request { "window.location = '/login'" }
            }
            Icon(name = "arrow-right-from-bracket", slot = "prefix")
            +"Sign out"
        }
    }
}
```

### Layouts — Composable page structure

```kotlin
@UI
fun AppLayout(content: Children) = render {
    html {
        head {
            link { rel = "stylesheet"; href = StyledBundler.bundle().path }
            script { type = "module"; src = StimulusScript.src }
        }
        body {
            hx { boost(enabled = true, inherited = true) }
            Header()
            main { content() }
            Toast(id = "toast-stack", placement = ToastPlacement.bottomEnd)
        }
    }
}
```

## Development

### Requirements

- JDK 25+ (JBR recommended)
- Gradle 9.2+

### Build

```bash
./gradlew build
```

### Run tests

```bash
./gradlew test
```

## License

See [LICENSE](LICENSE).
