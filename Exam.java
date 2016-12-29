package ius.iustudent.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Exam implements Parcelable {
    private String course;
    private String type;
    private Date date;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.course);
        dest.writeString(this.type);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
    }

    public Exam() {
    }

    protected Exam(Parcel in) {
        this.course = in.readString();
        this.type = in.readString();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
    }

    public static final Parcelable.Creator<Exam> CREATOR = new Parcelable.Creator<Exam>() {
        @Override
        public Exam createFromParcel(Parcel source) {
            return new Exam(source);
        }

        @Override
        public Exam[] newArray(int size) {
            return new Exam[size];
        }
    };
}
