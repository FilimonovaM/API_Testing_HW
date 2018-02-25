
package beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Result {

    @SerializedName("elevation")
    @Expose
    public Double elevation;
    @SerializedName("location")
    @Expose
    public Location location;
    @SerializedName("resolution")
    @Expose
    public Double resolution;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("elevation", elevation).append("location", location).append("resolution", resolution).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(elevation).append(location).append(resolution).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Result) == false) {
            return false;
        }
        Result rhs = ((Result) other);
        return new EqualsBuilder().append(elevation, rhs.elevation).append(location, rhs.location).append(resolution, rhs.resolution).isEquals();
    }

}
