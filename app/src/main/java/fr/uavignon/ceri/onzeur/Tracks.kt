package fr.uavignon.ceri.onzeur

data class Tracks (
    val track : List<Track>
)

data class Track (
    val idTrack : Int,
    val idAlbum : Int,
    val idArtist : Int,
    val idLyric : Int,
    val idIMVDB : Int,
    val strTrack : String,
    val strAlbum : String,
    val strArtist : String,
    val strArtistAlternate : String,
    val strGenre : String,
    val strMood : String,
    val strStyle : String,
    val strTheme : String
)

data class Albums (

    val album : List<Album>
)

data class Album (
    val idAlbum : Int,
    val idArtist : Int,
    val idLabel : Int,
    val strAlbum : String,
    val strAlbumStripped : String,
    val strArtist : String,
    val strArtistStripped : String,
    val intYearReleased : Int,
    val strStyle : String,
    val strGenre : String,
    val strLabel : String,
    val strAlbumThumb : String,
    val strAlbumThumbHQ : String,
    val strAlbumThumbBack : String,
    val strAlbumCDart : String,
    val strAlbumSpine : String,
    val strAlbum3DCase : String,
    val strAlbum3DFlat : String,
    val strAlbum3DFace : String,
    val strAlbum3DThumb : String,
)


