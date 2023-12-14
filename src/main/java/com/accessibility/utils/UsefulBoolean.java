package com.accessibility.utils;

import lombok.Getter;
import lombok.Setter;

/*
 * @author Max Karpov
 *
 */
class UsefulBoolean {
    /*
     * status of the accessibility test case
     *
     */
    @Getter @Setter
    private boolean isOK;
    /*
     * error message
     */
    @Getter @Setter
    private String error;

    public UsefulBoolean() {
        isOK = true;
    }
    public UsefulBoolean(final String error) {
        this.error = error;
        this.isOK = false;

    }

}
