package   com.ahmadullahpk.alldocumentreader.dataType;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class FavoriteFilesParcel implements Parcelable {
    public static final Parcelable.Creator<FavoriteFilesParcel> CREATOR = new Parcelable.Creator<FavoriteFilesParcel>() {
        public FavoriteFilesParcel createFromParcel(Parcel parcel) {
            return new FavoriteFilesParcel(parcel);
        }

        public FavoriteFilesParcel[] newArray(int i) {
            return new FavoriteFilesParcel[i];
        }
    };
    public ArrayList<String> favoriteFilesList;

    public int describeContents() {
        return 0;
    }

    private FavoriteFilesParcel() {
    }

    protected FavoriteFilesParcel(Parcel parcel) {
        this.favoriteFilesList = parcel.createStringArrayList();
    }

    public static FavoriteFilesParcel initFavoriteFileList() {
        FavoriteFilesParcel favoriteFilesParcel = new FavoriteFilesParcel();
        favoriteFilesParcel.favoriteFilesList = new ArrayList<>();
        return favoriteFilesParcel;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(this.favoriteFilesList);
    }
}
