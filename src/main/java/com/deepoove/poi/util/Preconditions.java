/*
 * Copyright 2014-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.util;

public final class Preconditions {

    private Preconditions() {}

    public static void checkMinimumVersion(String currentVer, String minimumVer,
            String message) {
        int ret = 0;
        try {
            ComparableVersion currentVersion = new ComparableVersion(currentVer);
            ComparableVersion minimumVersion = new ComparableVersion(minimumVer);
            ret = currentVersion.compareTo(minimumVersion);
        } catch (Exception e) {
            // not strict compare
            return;
        }
        if (ret < 0) { throw new IllegalStateException(message); }
    }

    static class ComparableVersion implements Comparable<ComparableVersion> {
        private final int first;
        private final int second;
        private final int third;

        public ComparableVersion(String version) {
            // eg. 3.16-beta1
            int separator = version.indexOf('-');
            String actualVersion = separator == -1 ? version : version.substring(0, separator);
            String[] split = actualVersion.split("\\.");
            int length = split.length;
            first = Integer.valueOf(split[0]);
            second = length > 1 ? Integer.valueOf(split[1]) : 0;
            third = length > 2 ? Integer.valueOf(split[2]) : 0;
        }

        @Override
        public int compareTo(ComparableVersion obj) {
            if (this == obj) return 0;
            if (this.first == obj.first) {
                if (this.second == obj.second) {
                    return this.third - obj.third;
                } else {
                    return this.second - obj.second;
                }
            }
            return this.first - obj.first;
        }
    }

}
