package limaru.railheart.extras

import com.bergerkiller.bukkit.common.config.ConfigurationNode
import com.bergerkiller.bukkit.tc.controller.MinecartMember
import com.bergerkiller.bukkit.tc.events.SignActionEvent
import com.bergerkiller.bukkit.tc.events.SignChangeActionEvent
import com.bergerkiller.bukkit.tc.signactions.SignAction
import com.bergerkiller.bukkit.tc.signactions.SignActionType
import com.bergerkiller.bukkit.tc.utils.SignBuildOptions
import java.util.function.Consumer

class SwapSign : SignAction() {
  override fun match(info: SignActionEvent): Boolean {
    return info.isType("swap", "swapdoor")
  }

  private fun swap(member: MinecartMember<*>) {
    val fullConfig = member.properties.model.config
    val attachments = fullConfig.getNode("attachments").nodes
    if (attachments != null) {
      for (node in attachments) {
        val attachment = member.properties.model.config.getNode("attachments")
        attachment[node.path] = s(node)
        fullConfig["attachments"] = attachment
        member.properties.model.update(fullConfig)
      }
    }
  }

  private fun s(attachmentNode: ConfigurationNode): ConfigurationNode {
    val attachments = attachmentNode.getNode("attachments").nodes
    if (attachments != null) {
      for (node in attachments) s(node)
    }
    val animations = attachmentNode.getNode("animations")
    val animationNames = animations.keys
    if (animationNames.contains("door_R")) {
      animationNames.forEach(Consumer { name: String ->
        if (name.startsWith("door_R")) {
          // set to new node
          animations["door_L" + name.substring(6)] = animations.getNode(name)
          // remove
          animations.remove(name)
          attachmentNode["animations"] = animations
        }
      })
    } else if (animationNames.contains("door_L")) {
      animationNames.forEach(Consumer { name: String ->
        if (name.startsWith("door_L")) {
          // set to new node
          animations["door_R" + name.substring(6)] = animations.getNode(name)
          // remove
          animations.remove(name)
          attachmentNode["animations"] = animations
        }
      })
    }
    return attachmentNode
  }

  override fun execute(info: SignActionEvent) {
    // When a [train] sign is placed, activate when powered by redstone when the train
    // goes over the sign, or when redstone is activated.
    if (info.isTrainSign
      && info.isAction(SignActionType.GROUP_ENTER, SignActionType.REDSTONE_ON)
      && info.isPowered && info.hasGroup()
    ) {
      for (member in info.group) {
        swap(member)
      }
    }

    // When a [cart] sign is placed, activate when powered by redstone when each cart
    // goes over the sign, or when redstone is activated.
    else if (info.isCartSign
      && info.isAction(SignActionType.MEMBER_ENTER, SignActionType.REDSTONE_ON)
      && info.isPowered && info.hasMember()
    ) {
      swap(info.member)
    }
  }

  override fun build(event: SignChangeActionEvent): Boolean {
    return SignBuildOptions.create()
      .setName("door swapper")
      .setDescription("swap left and right door animation names")
      .handle(event.player)
  }
}