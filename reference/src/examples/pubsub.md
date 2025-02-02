# Durable PubSub

```adama

@static {
  // anyone can create
  create(who) { return true; }
}

@connected (who) {
   // let everyone connect; sure, what can go wrong
  return true;
}

// we build a table of publishes with who published it and when they did so
record Publish {
  public client who;
  public long when;
  public string payload;
}

table<Publish> _publishes;

// since tables are private, we expose all publishes to all connected people
public formula publishes = iterate _publishes order by when asc;

// we wrap a payload inside a message
message PublishMessage {
  string payload;
}

// and then open a channel to accept the publish from any connected client
channel publish(client who, PublishMessage message) {
  _publishes <- {who: who, when: Time.now(), payload: message.payload };

  // At this point, we encounter a key problem with maintaining a
  // log of publishes. Namely, the log is potentially infinite, so
  // we have to leverage some product intelligence to reduce it to
  // a reasonably finite set which is important for the product.

  // First, we age out publishes too old (sad face)
  (iterate _publishes
     where when < Time.now() - 60000).delete();

  // Second, we hard cap the publishes biasing younger ones
  (iterate _publishes
     order by when desc
     offset 100).delete();
}

```