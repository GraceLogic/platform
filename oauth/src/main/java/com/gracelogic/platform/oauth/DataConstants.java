package com.gracelogic.platform.oauth;

import java.util.UUID;

public class DataConstants {

    public enum OAuthProviders {
        VK(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e71")),
        OK(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e72")),
        INSTAGRAM(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e73")),
        FACEBOOK(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e74")),
        TWITTER(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e75")),
        GOOGLE(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e76")),
        LINKEDIN(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e77")),
        ESIA(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e78"));

        private UUID value;

        OAuthProviders(UUID value) {
            this.value = value;
        }

        public UUID getValue() {
            return value;
        }
    }

    public enum OAuthIdentifierTypes {
        VK(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e81")),
        OK(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e82")),
        INSTAGRAM(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e83")),
        FACEBOOK(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e84")),
        TWITTER(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e85")),
        GOOGLE(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e86")),
        LINKEDIN(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e87")),
        ESIA(UUID.fromString("54480ce1-00eb-4179-a2b6-f74daa6b9e88"));

        private UUID value;

        OAuthIdentifierTypes(UUID value) {
            this.value = value;
        }

        public UUID getValue() {
            return value;
        }
    }
}
