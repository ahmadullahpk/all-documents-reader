package   com.ahmadullahpk.alldocumentreader.dataType;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class ImageParcelable implements Parcelable {

    private Uri uri;

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    private long id;
    private boolean isSelected;
    private boolean isVideo;
    private String name;
    private String path;


    public ImageParcelable(String path, boolean isSelected) {
        this.path = path;
        this.isSelected = isSelected;
    }



    public ImageParcelable(long id, String name, String path, boolean isVideo) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.isVideo = isVideo;
    }

    public ImageParcelable() {

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static Creator<ImageParcelable> getCREATOR() {
        return CREATOR;
    }

    protected ImageParcelable(Parcel in) {
        id = in.readLong();
        isSelected = in.readByte() != 0;
        isVideo = in.readByte() != 0;
        name = in.readString();
        path = in.readString();
    }

    public static final Creator<ImageParcelable> CREATOR = new Creator<ImageParcelable>() {
        @Override
        public ImageParcelable createFromParcel(Parcel in) {
            return new ImageParcelable(in);
        }

        @Override
        public ImageParcelable[] newArray(int size) {
            return new ImageParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeByte((byte) (isVideo ? 1 : 0));
        dest.writeString(name);
        dest.writeString(path);
    }
}
