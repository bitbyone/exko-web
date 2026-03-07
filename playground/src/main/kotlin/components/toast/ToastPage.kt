package io.exko.sandbox.uikit.components.toast

import io.exko.html.Component
import io.exko.html.UI
import io.exko.html.fragment
import io.exko.html.js
import io.exko.html.view
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.button.Button
import io.exko.webawesome.components.select.Select
import io.exko.webawesome.components.option.Option
import io.exko.webawesome.components.toast.Toast
import io.exko.webawesome.props.Size
import io.exko.webawesome.props.ToastPlacement
import io.exko.webawesome.props.Variant
import kotlinx.html.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/ui-kit/toast")
class ToastPageController {

    @GetMapping
    fun render() = view(ToastPage(), ::PlaygroundLayout)
}

@UI
fun ToastPage() = fragment {
    h1 { +"Toast" }

    Toast(id = "toast-basic")
    Toast(id = "toast-variants")
    Toast(id = "toast-sizes")
    Toast(id = "toast-icons")
    Toast(id = "toast-duration")
    Toast(id = "toast-placement")

    Basic()
    br
    Variants()
    br
    Sizes()
    br
    WithIcon()
    br
    Duration()
    br
    Placements()
}

@UI
fun Component.Basic() {
    h2 { +"Basic" }
    Button(id = "btn-basic") { +"Show Toast" }
    js("""
        document.getElementById('btn-basic').addEventListener('click', () => {
            document.getElementById('toast-basic').create('This is a toast notification!');
        });
    """)
}

@UI
fun Component.Variants() {
    h2 { +"Variants" }
    div("wa-cluster") {
        id = "variant-buttons"
        Variant.entries.forEach { variant ->
            Button(variant = variant) {
                attributes["data-variant"] = variant.realValue
                +variant.name
            }
        }
    }
    js("""
        const variantIcons = {
            neutral: 'circle-info',
            brand: 'star',
            success: 'circle-check',
            warning: 'triangle-exclamation',
            danger: 'circle-exclamation'
        };
        document.getElementById('variant-buttons').addEventListener('click', event => {
            const button = event.target.closest('[data-variant]');
            if (!button) return;
            const variant = button.getAttribute('data-variant');
            document.getElementById('toast-variants').create('This is a ' + variant + ' notification', {
                variant: variant,
                icon: variantIcons[variant]
            });
        });
    """)
}

@UI
fun Component.Sizes() {
    h2 { +"Sizes" }
    div("wa-cluster") {
        id = "size-buttons"
        Size.entries.forEach { size ->
            Button {
                attributes["data-size"] = size.realValue
                +size.name
            }
        }
    }
    js("""
        document.getElementById('size-buttons').addEventListener('click', event => {
            const button = event.target.closest('[data-size]');
            if (!button) return;
            const size = button.getAttribute('data-size');
            document.getElementById('toast-sizes').create('This is a ' + size + ' notification', {
                size: size,
                icon: 'circle-info'
            });
        });
    """)
}

@UI
fun Component.WithIcon() {
    h2 { +"With Icons" }
    div("wa-cluster") {
        Button(id = "btn-icon-success", variant = Variant.success) { +"Success" }
        Button(id = "btn-icon-danger", variant = Variant.danger) { +"Error" }
        Button(id = "btn-icon-warning", variant = Variant.warning) { +"Warning" }
    }
    js("""
        document.getElementById('btn-icon-success').addEventListener('click', () => {
            document.getElementById('toast-icons').create('Your file has been uploaded successfully!', {
                variant: 'success',
                icon: 'circle-check'
            });
        });
        document.getElementById('btn-icon-danger').addEventListener('click', () => {
            document.getElementById('toast-icons').create('Something went wrong. Please try again.', {
                variant: 'danger',
                icon: 'circle-exclamation'
            });
        });
        document.getElementById('btn-icon-warning').addEventListener('click', () => {
            document.getElementById('toast-icons').create('Please check your input.', {
                variant: 'warning',
                icon: 'triangle-exclamation'
            });
        });
    """)
}

@UI
fun Component.Duration() {
    h2 { +"Duration" }
    div("wa-cluster") {
        id = "duration-buttons"
        Button(id = "btn-duration-0") { +"Persistent (0)" }
        Button(id = "btn-duration-3") { +"3 seconds" }
        Button(id = "btn-duration-10") { +"10 seconds" }
    }
    js("""
        document.getElementById('btn-duration-0').addEventListener('click', () => {
            document.getElementById('toast-duration').create('Dismiss me manually!', {
                duration: 0,
                icon: 'clock'
            });
        });
        document.getElementById('btn-duration-3').addEventListener('click', () => {
            document.getElementById('toast-duration').create('Disappears in 3s', {
                duration: 3000,
                icon: 'clock'
            });
        });
        document.getElementById('btn-duration-10').addEventListener('click', () => {
            document.getElementById('toast-duration').create('Disappears in 10s', {
                duration: 10000,
                icon: 'clock'
            });
        });
    """)
}

@UI
fun Component.Placements() {
    h2 { +"Placement" }
    div("wa-cluster") {
        id = "placement-buttons"
        ToastPlacement.entries.forEach { placement ->
            Button(id = "btn-placement-${placement.realValue}") {
                attributes["data-placement"] = placement.realValue
                +placement.realValue
            }
        }
    }
    js("""
        document.getElementById('placement-buttons').addEventListener('click', event => {
            const button = event.target.closest('[data-placement]');
            if (!button) return;
            const placement = button.getAttribute('data-placement');
            const toast = document.getElementById('toast-placement');
            toast.placement = placement;
            toast.create('Placement: ' + placement, {
                variant: 'brand',
                icon: 'location-dot'
            });
        });
    """)
}
