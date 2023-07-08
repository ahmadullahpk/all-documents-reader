package com.ahmadullahpk.alldocumentreader.manageui;


import androidx.annotation.NonNull;

public abstract class ImageCardViewHome {

    private final int backgroundImageResource;

    public static class Background extends ImageCardViewHome {

        private final int backgroundImageResource;

        @Override
        public boolean equals(Object obj) {
            if (this != obj) {
                if (obj instanceof Background) {
                    return getBackgroundImageResource() == ((Background) obj).getBackgroundImageResource();
                }
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            return getBackgroundImageResource();
        }

        @NonNull
        public String toString() {
            return "Background(backgroundImageResource=" +
                    getBackgroundImageResource() +
                    ")";
        }


        public Background(int i) {
            super(i);
            this.backgroundImageResource = i;
        }

        public int getBackgroundImageResource() {
            return this.backgroundImageResource;
        }
    }

    public static class Res extends ImageCardViewHome {

        private final int drawableRes;

        private final int backgroundImageResource;

        @Override
        public boolean equals(Object obj) {
            if (this != obj) {
                if (obj instanceof Res) {
                    Res res = (Res) obj;
                    if (this.drawableRes == res.drawableRes) {
                        return getBackgroundImageResource() == res.getBackgroundImageResource();
                    }
                }
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            return (this.drawableRes * 31) + getBackgroundImageResource();
        }

        @NonNull
        public String toString() {
            return "Res(drawableRes=" +
                    this.drawableRes +
                    ", backgroundImageResource=" +
                    getBackgroundImageResource() +
                    ")";
        }

        public Res(int i, int i2) {
            super(i2);
            this.drawableRes = i;
            this.backgroundImageResource = i2;
        }

        public int getBackgroundImageResource() {
            return this.backgroundImageResource;
        }

        public int getResDrawable() {
            return this.drawableRes;
        }
    }

    public static class Svg extends ImageCardViewHome {

        private final String url;

        private final int backgroundImageResource;

        @Override
        public int hashCode() {
            String str = this.url;
            return ((str != null ? str.hashCode() : 0) * 31) + getBackgroundImageResource();
        }

        @NonNull
        public String toString() {
            String sb = "Svg(url=" +
                    this.url +
                    ", backgroundImageResource=" +
                    getBackgroundImageResource() +
                    ")";
            return sb;
        }

        public Svg(String str, int i) {
            super(i);

            this.url = str;
            this.backgroundImageResource = i;
        }

        public int getBackgroundImageResource() {
            return this.backgroundImageResource;
        }

        public final String getUrl() {
            return this.url;
        }
    }

    public static class Url extends ImageCardViewHome {

        private final String url;

        private final int progress;

        private final int backgroundImageResource;


        @Override
        public int hashCode() {
            String str = this.url;
            return ((((str != null ? str.hashCode() : 0) * 31) + this.progress) * 31) + getBackgroundImageResource();
        }

        @NonNull
        public String toString() {
            return "Url(url=" +
                    this.url +
                    ", progress=" +
                    this.progress +
                    ", backgroundImageResource=" +
                    getBackgroundImageResource() +
                    ")";
        }


        public Url(String str, int i, int i2) {
            super(i2);

            this.url = str;
            this.progress = i;
            this.backgroundImageResource = i2;
        }

        public int getBackgroundImageResource() {
            return this.backgroundImageResource;
        }

        public String getUrl() {
            return this.url;
        }

        public int getProgress() {

            return this.progress;
        }
    }

    private ImageCardViewHome(int i) {
        this.backgroundImageResource = i;
    }


    public int getBackgroundImageResource() {
        return this.backgroundImageResource;
    }
}
