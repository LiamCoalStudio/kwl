package com.liamcoalstudio.kettle.kwl.entity

import java.io.DataInputStream
import kotlin.math.max

abstract class LivingEntity : Entity() {
    // These are here because primitive types can't be `lateinit`
    private var health1 = 0.0
    private var damageTaken1 = 0.0
    private var damageHealed1 = 0.0

    /**
     * Health of this entity.
     */
    // In the form of a get/set because primitive types.
    var health: Double
        protected set(v) { health1 = v }
        get() = health1

    /**
     * The amount of damage this entity has taken.
     *
     * Statistics?
     */
    // In the form of a get/set because primitive types.
    var damageTaken: Double
        protected set(v) { damageTaken1 = v }
        get() = damageTaken1

    /**
     * The amount of [damageTaken] damage that has been healed.
     *
     * Could possibly be used for difficult entities so that they
     * could become "easier" if enough damage was healed.
     */
    // In the form of a get/set because primitive types.
    var damageHealed: Double
        protected set(v) { damageHealed1 = v }
        get() = damageHealed1

    /**
     * The multiplier for [healDamage]'s health argument when subtracting
     * from [damageTaken]. Can be changed when the entity becomes "easier"
     * so that the player earns less "points" for killing it.
     */
    protected var healDamagePenalty = 1.0

    /**
     * Calls the default implementation ([Entity.read]), adding these:
     * - [health]: [Double]
     * - [damageTaken]: [Double]
     * - [damageHealed]: [Double]
     */
    override fun read(reader: DataInputStream): Entity {
        super.read(reader)
        health = reader.readDouble()
        damageTaken = reader.readDouble()
        damageHealed = reader.readDouble()
        healDamagePenalty = reader.readDouble()
        return this
    }

    /**
     * Damages this entity by [health] health.
     *
     * Decreases [LivingEntity.health], and increases
     * [LivingEntity.damageTaken]
     */
    fun damage(health: Double) {
        if(this.health > 0.0 && this.health - health < 0.0)
            onDeath(health)
        else if(this.health > 0.0)
            onDamage(health)
        this.health -= health
        damageTaken += health
    }

    /**
     * Heals this entity by [health] health
     *
     * Increases [LivingEntity.health]
     */
    fun heal(health: Double) {
        onHeal(health, false)
        this.health += health
    }

    /**
     * Heals damage taken by the entity and:
     * - Increases [damageHealed] by [health]
     * - Decreases [damageTaken] by [health] * [healDamagePenalty]
     */
    fun healDamage(health: Double) {
        onHeal(health, true)
        this.health += health
        damageTaken -= health * healDamagePenalty
        damageTaken = max(damageTaken, 0.0)
        damageHealed += health
    }

    /** Called when [LivingEntity.damage] is called */
    protected open fun onDamage(health: Double) {}
    /** Called when [LivingEntity.damage] is called and the resulting health is less than `0.0` */
    protected open fun onDeath(health: Double) {}
    /** Called when [LivingEntity.heal] or [LivingEntity.healDamage] is called */
    protected open fun onHeal(health: Double, isDamageHeal: Boolean) {}
}