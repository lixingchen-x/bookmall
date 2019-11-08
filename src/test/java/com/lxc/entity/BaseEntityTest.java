package com.lxc.entity;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class BaseEntityTest {

    @Test
    public void equals_shouldEqual_ifIdIsSame() {

        BaseEntity baseEntity1 = createBaseWithId(1);
        BaseEntity baseEntity2 = createBaseWithId(1);

        assertThat(baseEntity1).isEqualTo(baseEntity2);
    }

    @Test
    public void equals_shouldHaveReflexivity() {

        BaseEntity baseEntity = new BaseEntity();

        assertThat(baseEntity).isEqualTo(baseEntity);
    }

    @Test
    public void equals_shouldHaveSymmetry() {

        BaseEntity baseEntity1 = createBaseWithId(1);
        BaseEntity baseEntity2 = createBaseWithId(1);

        assertThat(baseEntity1).isEqualTo(baseEntity2);
        assertThat(baseEntity2).isEqualTo(baseEntity1);
    }

    @Test
    public void equals_shouldHaveConsistency() {

        BaseEntity baseEntity1 = createBaseWithId(1);
        BaseEntity baseEntity2 = createBaseWithId(1);

        assertThat(baseEntity1).isEqualTo(baseEntity2);
        assertThat(baseEntity1).isEqualTo(baseEntity2);
    }

    @Test
    public void equals_shouldHaveTransitivity() {

        BaseEntity baseEntity1 = createBaseWithId(1);
        BaseEntity baseEntity2 = createBaseWithId(1);
        BaseEntity baseEntity3 = createBaseWithId(1);

        assertThat(baseEntity1).isEqualTo(baseEntity2);
        assertThat(baseEntity2).isEqualTo(baseEntity3);
        assertThat(baseEntity1).isEqualTo(baseEntity3);
    }

    @Test
    public void equals_shouldBeFalse_ifOtherIsNull() {

        BaseEntity baseEntity = createBaseWithId(1);
        BaseEntity other = null;

        assertThat(baseEntity).isNotEqualTo(other);
    }

    @Test
    public void hashCode_shouldEqual_whenComparedWithSameId() {

        BaseEntity baseEntity1 = createBaseWithId(1);
        BaseEntity baseEntity2 = createBaseWithId(1);

        assertThat(baseEntity1.hashCode()).isEqualTo(baseEntity2.hashCode());
    }

    @Test
    public void hashCode_shouldNotEqual_whenComparedWithDifferentId() {

        BaseEntity baseEntity1 = createBaseWithId(1);
        BaseEntity baseEntity2 = createBaseWithId(2);

        assertThat(baseEntity1.hashCode()).isNotEqualTo(baseEntity2.hashCode());
    }

    private BaseEntity createBaseWithId(Integer id) {

        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setId(id);
        return baseEntity;
    }
}
