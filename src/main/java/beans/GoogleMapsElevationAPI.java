
package beans;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GoogleMapsElevationAPI {

    @SerializedName("results")
    @Expose
    public List<Result> results = new ArrayList<Result>();
    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("error message")
    @Expose
    public String errorMessage;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("results", results).append("status", status).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(results).append(status).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GoogleMapsElevationAPI) == false) {
            return false;
        }
        GoogleMapsElevationAPI rhs = ((GoogleMapsElevationAPI) other);
        return new EqualsBuilder().append(results, rhs.results).append(status, rhs.status).isEquals();
    }

}
