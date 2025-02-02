# Chat

```adama

@static {
  // anyone can create
  create(who) { return true; }
  invent(who) { return true; }
  maximum_history = 250;
}

// let anyone into the document
@connected (who) {
  return true;
}

// the lines of chat
record Line {
  public client who;
  public string what;
  public long when;
}

// the chat table
table<Line> _chat;

// how someone communicates to the document
message Say {
  string what;
}

// the "channel" which enables someone to say something
channel say(client who, Say what) {
  // ingest the line into the chat
  _chat <- {who:who, what:what.what, when: Time.now()};

  (iterate _chat order by when desc offset 50).delete();
}

// emit the data out
public formula chat = iterate _chat;

```