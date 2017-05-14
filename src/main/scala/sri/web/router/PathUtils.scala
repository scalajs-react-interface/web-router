package sri.web.router

trait PathUtils {

  def prefixSlashAndRemoveTrailingSlashes(in: String) =
    if (in.startsWith("/")) in.removeTrailingSlash
    else "/" + in.removeTrailingSlash
}
