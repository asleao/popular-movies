plugins {
    id("popularmovies.android.library")
}

android {
    namespace = Namespaces.coreDomainApi
}

dependencies {
    api(libs.jodatime)
}