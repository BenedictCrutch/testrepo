package net.countercraft.movecraft.processing.functions;

import net.countercraft.movecraft.craft.CraftType;
import net.countercraft.movecraft.processing.MovecraftWorld;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a data carrying predicate, which can return a message depending on the success state of its evaluation
 * @param <T>
 */
@FunctionalInterface public interface DetectionPredicate<T> extends TetradicPredicate<T, CraftType, MovecraftWorld, CommandSender>{

    Result validate(@NotNull T t, @NotNull CraftType type, @NotNull MovecraftWorld world, @Nullable CommandSender player);

    default DetectionPredicate<T> or(DetectionPredicate<T> other){
        return (t, type, world, player) -> {
            var result = this.validate(t, type, world, player);
            if(result.isSucess()){
                return result;
            }
            return other.validate(t,type,world,player);
        };
    }

    default DetectionPredicate<T> and(DetectionPredicate<T> other){
        return (t, type, world, player) -> {
            var result = this.validate(t, type, world, player);
            if(!result.isSucess()){
                return result;
            }
            return other.validate(t,type,world,player);
        };
    }

}