package com.softserve.webtester.model;

import java.io.Serializable;

/**
 * The BuildVersion class represents {@code BuilVersion} entity stored in the
 * database which inherited from abstract class AbstractDomain.
 *
 * @author Anton Mykytiuk
 */

public class BuildVersion extends AbstractDomain implements Serializable {

    private static final long serialVersionUID = 5450435696043755309L;

    public BuildVersion(int id, String name, String description, boolean deleted) {
        super(id, name, description, deleted);
    }

    public BuildVersion() {
    }
}
