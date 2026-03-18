package io.exko.htmx.spring

import org.springframework.context.ApplicationEvent

class ExkoRefreshEvent(obj: Any) : ApplicationEvent(obj)
