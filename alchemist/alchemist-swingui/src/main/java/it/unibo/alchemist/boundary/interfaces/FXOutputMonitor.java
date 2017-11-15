package it.unibo.alchemist.boundary.interfaces;

import it.unibo.alchemist.boundary.gui.effects.EffectFX;
import it.unibo.alchemist.boundary.gui.effects.EffectGroup;
import it.unibo.alchemist.model.interfaces.Concentration;
import it.unibo.alchemist.model.interfaces.Environment;
import it.unibo.alchemist.model.interfaces.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.Collection;

/**
 * {@code OutputMonitor} that handles the graphical part of the simulation in JavaFX.
 *
 * @param <T> the {@link Concentration} type
 */
public interface FXOutputMonitor<T> extends OutputMonitor<T> {

    /**
     * Getter method for the steps.
     *
     * @return how many simulation steps this monitor updates the graphics
     */
    int getStep();

    /**
     * Setter method fo the steps.
     *
     * @param step How many steps should be computed by the engine for the
     *             display to update the graphics
     */
    void setStep(int step);

    /**
     * @return true if this monitor is trying to draw in realtime
     */
    boolean isRealTime();

    /**
     * If enabled, the monitor tries to synchronize the simulation time with the
     * real time, slowing down the simulator if needed. If the simulation is
     * slower than the real time, then the display refreshes fast enough to keep
     * the default frame rate.
     *
     * @param realTime true for the real time mode
     */
    void setRealTime(boolean realTime);

    /**
     * Repaints this {@link Canvas}' {@link GraphicsContext} by drawing all the {@link EffectFX Effect}s of each
     * {@link Node} of the specified {@link Environment}.
     */
    void repaint();

    /**
     * Getter method for the {@link EffectFX Effects} to draw.
     *
     * @return the current {@code Effects} to draw
     */
    Collection<EffectGroup> getEffects();

    /**
     * Setter method for the effects to draw.
     * <p>
     * All previous set {@link EffectFX Effects} are removed.
     *
     * @param effects the {@code Effects} to draw
     */
    void setEffects(Collection<EffectGroup> effects);

    /**
     * Add all the {@link EffectGroup}s in the collection to the {@link EffectFX Effects} to draw.
     *
     * @param effects the {@link EffectGroup}s to draw
     * @see Collection#addAll(Collection)
     */
    void addEffects(final Collection<EffectGroup> effects);

    /**
     * Add the {@link EffectGroup} in the collection to the {@link EffectFX Effects} to draw.
     *
     * @param effects the {@link EffectGroup} to draw
     * @see Collection#add(Object)
     */
    void addEffectGroup(final EffectGroup effects);
}