package nl.c2c.ac.devops.auditcase;

import lombok.Builder;
import lombok.Value;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Value
@Builder(builderMethodName = "builderInternal")
public class AcVersion implements Comparable<AcVersion> {

    int major;

    int minor;

    int patch;

    int rc; //1.0.0-rc.1

    public static AcVersionBuilder builder() {
        return AcVersion.builderInternal();
    }

    public static AcVersionBuilder builder(final String versionarg) {

        String version = versionarg.trim();
        if (versionarg.isEmpty()) {
            version = "0.0.0";
        }

        final String[] values = version.split(Pattern.quote("."), -1);
        final List<String> versionList = Arrays.asList(values);

        final int major;
        if (!versionList.isEmpty()) {
            major = Integer.parseInt(versionList.get(0).isEmpty() ? "0" : versionList.get(0));
        } else {
            major = 0;
        }
        final int minor;
        if (versionList.size() > 1) {
            minor = Integer.parseInt(versionList.get(1).isEmpty() ? "0" : versionList.get(1));
        } else {
            minor = 0;
        }
        final int patch;
        if (versionList.size() > 2) {
            if (versionList.get(2).contains("-rc")) {
                final List<String> versionListRc = Arrays.asList(versionList.get(2).split(Pattern.quote("-rc"), -1));
                patch = Integer.parseInt(versionListRc.get(0).isEmpty() ? "0" : versionListRc.get(0));
            } else {
                patch = Integer.parseInt(versionList.get(2).isEmpty() ? "0" : versionList.get(2));
            }
        } else {
            patch = 0;
        }

        final int rc;
        if (versionList.size() > 3 && versionList.get(2).contains("-rc")) {
            rc = Integer.parseInt(versionList.get(3).isEmpty() ? "0" : versionList.get(3));
        } else {
            rc = 0;
        }

        return

          builderInternal().

            major(major).

            minor(minor).

            patch(patch).

            rc(rc);

    }

    @Override
    public String toString() {
        return String.join(".", String.valueOf(getMajor()), String.valueOf(getMinor()), String.valueOf(getPatch()));
    }

    public String toStringWithRc() {
        return String.join(".", String.valueOf(getMajor()), String.valueOf(getMinor()), getPatch()
          +
          (getRc() > 0 ? ("-rc." + getRc()) : ""));
    }

    @Override
    public int compareTo(final AcVersion o) {
        if (this.getMajor() != o.getMajor()) {
            return Integer.compare(this.getMajor(), o.getMajor());
        }
        if (this.getMinor() != o.getMinor()) {
            return Integer.compare(this.getMinor(), o.getMinor());
        }
        if (this.getPatch() != o.getPatch()) {
            return Integer.compare(this.getPatch(), o.getPatch());
        }
        if (this.getRc() != o.getRc()) {
            return Integer.compare(this.getRc(), o.getRc());
        }
        return 0;
    }

    /**
     * @return new version calculated from the existing version
     */
    public AcVersion toNewPatch() {

        int patchPlus = this.getPatch();
        patchPlus++;

        return AcVersion
          .builderInternal().major(this.getMajor()).minor(this.getMinor()).patch(patchPlus)
          .build();
    }
}
