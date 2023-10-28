package game.rimu.constants

import game.rimu.data.Skin

enum class RimuSetting(val default: Any)
{

    // UI

    /**
     * The UI scale factor, by default `1.0`.
     */
    UI_SCALE_FACTOR(1f),

    /**
     * The skin key, by default [Skin.BASE].
     */
    UI_SKIN(Skin.BASE),

    /**
     * Determines if we use the beatmap skin instead of the user skin, by default `true`.
     */
    UI_USE_BEATMAP_SKIN(true),

    // Music

    /**
     * The music volume, by default `1.0`
     */
    MUSIC_VOLUME(1f);


    val key = name

}