package at.tugraz.software22.domain;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import at.tugraz.software22.R;

public class Language {

    public static List<Language> getLanguages(Resources resources) {
        ArrayList<Language> languages = new ArrayList();

        languages.add(new Language(
                resources.getString(R.string.language_en),
                "en",
                R.drawable.language_en
                ));
        languages.add(new Language(
                resources.getString(R.string.language_de),
                "de",
                R.drawable.language_de
                ));

        return languages;
    }

    private final String id_;
    private final String localized_string_;
    private final int drawable_;

    public Language(String localized_string_, String id_, int drawable_) {
        this.localized_string_ = localized_string_;
        this.id_ = id_;
        this.drawable_ = drawable_;
    }

    public String getLocalizedString() {
        return localized_string_;
    }

    public String getId() {
        return id_;
    }

    public int getDrawable() {
        return drawable_;
    }

    @Override
    public String toString() {
        return localized_string_;
    }
}
