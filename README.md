# 1Q Programming Test

This is definitely a WIP, and I didn't get quite as far along as I had wanted.
I'm going to continue trying to work on it up until our meeting, but I probably
won't get much further.

### Methodology

I used `akka-http-session`, configured for `jwt` to handle sessions. In
retrospect, it may have been better to simply "roll my own," as I spent a lot
of time trying to pick apart that library and understand how (and why) it
should be configured.

My setup isn't quite ideal, but I was able to get it set up so that a request
to `/login` will return a jwt in the `Set-Authorization` header. I have a
general understanding of how to use the directives to set the session for
subsequent requests, but I have not been able to implement it yet.

I tried using an in-memory h2 database as my backend, and using `slick` as a
query builder.  This wound up being a little bit of a time-sync as well. I feel
like I am getting there, but currently, I am still having a little bit of
dificulty getting results back.  I believe that I need to do a better job at
plumbing through the execution context properly.

### Conclusion

To be perfectly honest. This kicked my butt. I think there were more blind
spots in my knowledge of the language than I had anticipated. I have spent a
good bit of time poking around the docs and source code of the libraries used
in this project. I feel like I have a better handle than I did, but the
learning curve is going to be a little steeper than I had hoped.

I feel like I am starting to move in the right direction, but I am not yet able
to actually complete the requirements.
