package com.lxc.entity;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class BaseTest {

    @Test
    public void equals_IdIsSame_happyPath() {

        Base base1 = createBaseWithId(1);
        Base base2 = createBaseWithId(1);
        assertThat(base1).isEqualTo(base2);
    }

    @Test
    public void equals_Reflexivity_happyPath() {

        Base base = new Base();
        assertThat(base).isEqualTo(base);
    }

    @Test
    public void equals_Symmetry_happyPath() {

        Base base1 = createBaseWithId(1);
        Base base2 = createBaseWithId(1);
        assertThat(base1).isEqualTo(base2);
        assertThat(base2).isEqualTo(base1);
    }

    @Test
    public void equals_Consistency_happyPath() {

        Base base1 = createBaseWithId(1);
        Base base2 = createBaseWithId(1);
        assertThat(base1).isEqualTo(base2);
        assertThat(base1).isEqualTo(base2);
    }

    @Test
    public void equals_Transitivity_happyPath() {

        Base base1 = createBaseWithId(1);
        Base base2 = createBaseWithId(1);
        Base base3 = createBaseWithId(1);
        assertThat(base1).isEqualTo(base2);
        assertThat(base2).isEqualTo(base3);
        assertThat(base1).isEqualTo(base3);
    }

    @Test
    public void equals_shouldBeFalse_ifOtherIsNull() {

        Base base = createBaseWithId(1);
        Base other = null;
        assertThat(base).isNotEqualTo(other);
    }

    @Test
    public void hashCode_shouldEqual_whenComparedWithSameId() {

        Base base1 = createBaseWithId(1);
        int hashCode1 = base1.hashCode();
        Base base2 = createBaseWithId(1);
        int hashCode2 = base2.hashCode();
        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    public void hashCode_shouldNotEqual_whenComparedWithDifferentId() {

        Base base1 = createBaseWithId(1);
        int hashCode1 = base1.hashCode();
        Base base2 = createBaseWithId(2);
        int hashCode2 = base2.hashCode();
        assertThat(hashCode1).isNotEqualTo(hashCode2);
    }

    private Base createBaseWithId(Integer id) {

        Base base = new Base();
        base.setId(id);
        return base;
    }
}
