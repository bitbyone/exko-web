package io.exko.webawesome.blocks

import io.exko.html.Children
import io.exko.html.Component
import io.exko.html.Id
import io.exko.html.UI
import io.exko.styled.Css
import io.exko.styled.Styled
import io.exko.webawesome.components.avatar.Avatar
import io.exko.webawesome.components.tooltip.Tooltip
import io.exko.webawesome.props.Placement
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.li
import kotlinx.html.ul

@UI
fun Component.AvatarGroup(id: String? = null, children: Children) {
    div(classes = AvatarGroupStyles.avatarGroup) {
        id?.let { this.id = it }
        children()
    }
}

data class AvatarGroupData(
    val users: List<User>,
    val totalCount: Int,
) {
    data class User(
        val name: String,
        val initials: String,
    )
}

@UI
fun Component.AvatarGroupWithDetails(
    data: AvatarGroupData,
    avatarsCount: Int = 5,
    detailsCount: Int = data.users.count(),
    id: String? = null,
) {
    val groupId = id ?: Id.random
    AvatarGroup(id = groupId) {
        val users = data.users.take(avatarsCount)
        users.forEach {
            Avatar(initials = it.initials)
        }
        if (data.totalCount > avatarsCount) {
            Avatar(initials = "+" + (data.totalCount - users.count()))
        }
    }
    Tooltip(forId = groupId, placement = Placement.bottom, withoutArrow = true) {
        ul(classes = AvatarGroupStyles.unstyledList) {
            val tooltipUsers = data.users.take(detailsCount)
            tooltipUsers.forEach {
                li { +it.name }
            }
            if (tooltipUsers.count() < data.totalCount) {
                li { +("+" + (data.totalCount - tooltipUsers.count()) + " more users") }
            }
        }
    }
}

@Css
object AvatarGroupStyles : Styled() {
    val avatarGroup by css {
        """
        display: inline-block;

        & {
            wa-avatar:not(:first-of-type) {
                margin-left: calc(-1 * var(--wa-space-m));
            }

            wa-avatar {
                border: solid 2px var(--wa-color-surface-default);
                --size: var(--wa-font-size-3xl);
            }
        }
        """
    }

    val unstyledList by css {
        """
        list-style: none;
        margin: 0 auto;
        padding: 0;

        & li {
            margin: 0;
            padding: 0;
        }
        """
    }
}
