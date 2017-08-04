package it.unibo.alchemist.boundary.gui.effects.json;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.gson.reflect.TypeToken;

import it.unibo.alchemist.boundary.gui.effects.DrawColoredDot;
import it.unibo.alchemist.boundary.gui.effects.DrawDot;
import it.unibo.alchemist.boundary.gui.effects.DrawShapeFX;
import it.unibo.alchemist.boundary.gui.effects.EffectFX;
import it.unibo.alchemist.boundary.gui.effects.EffectGroup;
import it.unibo.alchemist.boundary.gui.effects.EffectStack;

/**
 * JUnit test for EffectSerializer class.
 */
public class EffectSerializerTest {

    /**
     * Temporary folder created before each test method, and deleted after each.
     */
    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    /**
     * Tests methods {@link EffectSerializer#effectGroupsFromFile(File)} and
     * {@link EffectSerializer#effectGroupsToFile(File, List)}.
     * 
     * @throws IOException
     *             if something goes wrong
     */
    @Test
    public void testMultipleEffectGroupsSerialization() throws IOException {
        final File file = folder.newFile();

        final List<EffectGroup> groups = initList();

        EffectSerializer.effectGroupsToFile(file, groups);

        final List<EffectGroup> deserialized = EffectSerializer.effectGroupsFromFile(file);

        Assert.assertTrue(groups.equals(deserialized));
    }

    /**
     * Tests serialization of a list of {@link EffectFX effect}s.
     * 
     * @throws IOException
     *             if something goes wrong
     */
    @Test
    public void testListOfEffectSerialization() throws IOException {
        final File file = folder.newFile();
        final Type type = new TypeToken<List<EffectFX>>() { }.getType();

        final List<EffectFX> effects = new ArrayList<>();
        effects.add(new DrawDot());
        effects.add(new DrawShapeFX());
        effects.add(new DrawColoredDot("Test"));

        final Writer writer = new FileWriter(file);
        EffectSerializer.getGSON().toJson(effects, type, writer);
        writer.close();

        final Reader reader = new FileReader(file);
        final List<EffectFX> deserialized = EffectSerializer.getGSON().fromJson(reader, type);
        reader.close();

        Assert.assertTrue(effects.equals(deserialized));
    }

    /**
     * Initializes and returns a list of {@link EffectGroup}s.
     * 
     * @return a list of {@code EffectGroups}
     */
    private List<EffectGroup> initList() {
        final List<EffectGroup> groups = new ArrayList<>();

        final EffectGroup group1 = new EffectStack();
        groups.add(group1);

        final EffectGroup group2 = new EffectStack("Group 2");
        groups.add(group2);

        final EffectGroup group3 = new EffectStack("Group 3");
        group3.setVisibility(false);
        // CHECKSTYLE:OFF
        group3.setTransparency(32);
        // CHECKSTYLE:On
        groups.add(group3);

        final EffectGroup group4 = new EffectStack();
        group4.add(new DrawDot());
        group4.add(new DrawDot("TestDot"));
        group4.add(new DrawShapeFX("TestShape"));
        groups.add(group4);

        final EffectGroup group5 = new EffectStack("Group 5");
        final DrawDot dot = new DrawDot("Dot 2");
        // CHECKSTYLE:OFF
        dot.setSize(7.0);
        // CHECKSTYLE:ON
        group5.add(dot);
        final DrawColoredDot colorDot = new DrawColoredDot();
        colorDot.setColor(Color.ORANGE);
        colorDot.setVisibility(false);
        group5.add(colorDot);
        group5.setVisibility(false);
        groups.add(group5);

        return groups;
    }

}