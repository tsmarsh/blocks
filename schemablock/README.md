# blockserver

The takes out most of the boilerplate, not that there is much for compojure. Blocks are only ever expected to accept a post, but they all do that, so this is getting pulled out as common code.

Future enhancements would be to wrap more monitoring and logging.

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## License

Copyright © 2020 FIXME
