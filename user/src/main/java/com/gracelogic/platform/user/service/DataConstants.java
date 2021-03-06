package com.gracelogic.platform.user.service;

import java.util.UUID;

public class DataConstants {
    public enum PassphraseEncryptors {
        OPEN(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e71")),
        SHA1_WITH_SALT(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e72"));
        private UUID value;

        PassphraseEncryptors(UUID value) {
            this.value = value;
        }

        public UUID getValue() {
            return value;
        }
    }

    public enum PassphraseTypes {
        USER_PASSWORD(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e71")),
        CHANGE_PASSWORD_VERIFICATION_CODE(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e72")),
        IDENTIFIER_VERIFICATION_CODE(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e73"));
        private UUID value;

        PassphraseTypes(UUID value) {
            this.value = value;
        }

        public UUID getValue() {
            return value;
        }
    }

    public enum PassphraseStates {
        ACTUAL(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e71")),
        ARCHIVE(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e72"));
        private UUID value;

        PassphraseStates(UUID value) {
            this.value = value;
        }

        public UUID getValue() {
            return value;
        }
    }

    public enum IdentifierTypes {
        USER_ID(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e71")),
        EMAIL(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e72")),
        PHONE(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e73")),
        LOGIN(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e74"));
        private UUID value;

        IdentifierTypes(UUID value) {
            this.value = value;
        }

        public UUID getValue() {
            return value;
        }
    }

    public enum UserApproveMethods {
        AUTO("AUTO"),
        MANUAL("MANUAL");

        private String value;

        UserApproveMethods(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum TemplateTypes {
        EMAIL_VERIFICATION(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e71")),
        SMS_VERIFICATION(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e72")),
        EMAIL_REPAIRING(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e73")),
        SMS_REPAIRING(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e74"));
        private UUID value;

        TemplateTypes(UUID value) {
            this.value = value;
        }

        public UUID getValue() {
            return value;
        }
    }

    public enum PassphraseGenerators {
        STATIC_FOUR_ZEROS(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e71")),
        RANDOM_FOUR_DIGITS(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e72")),
        RANDOM_SIX_DIGITS(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e73"));

        private UUID value;

        PassphraseGenerators(UUID value) {
            this.value = value;
        }

        public UUID getValue() {
            return value;
        }
    }
}