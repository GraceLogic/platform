package com.gracelogic.platform.filestorage.service;

import java.util.UUID;

/**
 * Author: Igor Parkhomenko
 * Date: 21.03.2015
 * Time: 16:39
 */
public class DataConstants {

    public enum StoreModes {
        LOCAL(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e71"));

        private UUID value;

        StoreModes(UUID value) {
            this.value = value;
        }

        public UUID getValue() {
            return value;
        }
    }
}