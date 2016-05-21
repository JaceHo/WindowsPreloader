package info.futureme.preloader.util;

/*
 * Copyright (C) 2011 The Android Open Source Project
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
import android.animation.TimeInterpolator;

public class EasingUtil {
    public static  float DOMAIN = 1.0f;
    public static  float DURATION = 1.0f;
    public static  float START = 0.0f;

    public static float easeInOut(float n){
        double q = 0.48f - n/DURATION / 1.04f,
                Q = Math.sqrt(0.1734f + q * q),
                x = Q - q,
                X = Math.pow(Math.abs(x), 1/3) * (x < 0 ? -1 : 1),
                y = -Q - q,
                Y = Math.pow(Math.abs(y), 1/3) * (y < 0 ? -1 : 1),
                t = X + Y + 0.5f;
        return (float) (DOMAIN * ((1 - t) * 3 * t * t + t * t * t) + START);
    }

    public static float easeOut(float n){
        return (float) (DOMAIN *Math.pow(n/DURATION, 0.48) + START);
    }

    public static float linear(float n){
        return DOMAIN*n/DURATION + START;
    }

    public static class Linear {
        public static final TimeInterpolator easeNone = new TimeInterpolator() {
            public float getInterpolation(float input) {
                return DOMAIN*(input/=DURATION) + START;
            }
        };
    }
    public static class Cubic {
        public static final TimeInterpolator easeIn = new TimeInterpolator() {
            public float getInterpolation(float input) {
                return DOMAIN*(input/=DURATION)*input*input + START;
            }
        };
        public static final TimeInterpolator easeOut = new TimeInterpolator() {
            public float getInterpolation(float input) {
                return DOMAIN*((input=input/DURATION-1)*input*input + 1) + START;
            }
        };
        public static final TimeInterpolator easeInOut = new TimeInterpolator() {
            public float getInterpolation(float input) {
                return ((input/=DURATION/2) < 1.0f) ?
                        (DOMAIN/2*input*input*input + START)
                        : (DOMAIN/2*((input-=2)*input*input + 2) + START);
            }
        };
    }
    public static class Quad {
        public static final TimeInterpolator easeIn = new TimeInterpolator() {
            public float getInterpolation (float input) {
                return DOMAIN*(input/=DURATION)*input + START;
            }
        };
        public static final TimeInterpolator easeOut = new TimeInterpolator() {
            public float getInterpolation(float input) {
                return -DOMAIN *(input/=DURATION)*(input-2) + START;
            }
        };
        public static final TimeInterpolator easeInOut = new TimeInterpolator() {
            public float getInterpolation(float input) {
                return ((input/=DURATION/2) < 1) ?
                        (DOMAIN/2*input*input + START)
                        : (-DOMAIN/2 * ((--input)*(input-2) - 1) + START);
            }
        };
    }
    public static class Quart {
        public static final TimeInterpolator easeIn = new TimeInterpolator() {
            public float getInterpolation(float input) {
                return DOMAIN*(input/=DURATION)*input*input*input + START;
            }
        };
        public static final TimeInterpolator easeOut = new TimeInterpolator() {
            public float getInterpolation(float input) {
                return -DOMAIN * ((input=input/DURATION-1)*input*input*input - 1) + START;
            }
        };
        public static final TimeInterpolator easeInOut = new TimeInterpolator() {
            public float getInterpolation(float input) {
                return ((input/=DURATION/2) < 1) ?
                        (DOMAIN/2*input*input*input*input + START)
                        : (-DOMAIN/2 * ((input-=2)*input*input*input - 2) + START);
            }
        };
    }
    public static class Quint {
        public static final TimeInterpolator easeIn = new TimeInterpolator() {
            public float getInterpolation(float input) {
                return DOMAIN*(input/=DURATION)*input*input*input*input + START;
            }
        };
        public static final TimeInterpolator easeOut = new TimeInterpolator() {
            public float getInterpolation(float input) {
                return DOMAIN*((input=input/DURATION-1)*input*input*input*input + 1) + START;
            }
        };
        public static final TimeInterpolator easeInOut = new TimeInterpolator() {
            public float getInterpolation(float input) {
                return ((input/=DURATION/2) < 1) ?
                        (DOMAIN/2*input*input*input*input*input + START)
                        : (DOMAIN/2*((input-=2)*input*input*input*input + 2) + START);
            }
        };
    }
    public static class Sine {
        public static final TimeInterpolator easeIn = new TimeInterpolator() {
            public float getInterpolation(float input) {
                return -DOMAIN * (float) Math.cos(input/DURATION * (Math.PI/2)) + DOMAIN + START;
            }
        };
        public static final TimeInterpolator easeOut = new TimeInterpolator() {
            public float getInterpolation(float input) {
                return DOMAIN * (float) Math.sin(input/DURATION * (Math.PI/2)) + START;
            }
        };
        public static final TimeInterpolator easeInOut = new TimeInterpolator() {
            public float getInterpolation(float input) {
                return -DOMAIN/2 * ((float)Math.cos(Math.PI*input/DURATION) - 1.0f) + START;
            }
        };
    }
}
