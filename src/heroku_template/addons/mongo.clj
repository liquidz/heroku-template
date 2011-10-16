(ns heroku-template.addons.mongo
  (:use
    somnium.congomongo
    [somnium.congomongo.config :only [*mongo-config*]]))

(declare init-mongo-collection)

(defn- split-mongo-url [url] ; {{{
  "Parses mongodb url from heroku, eg. mongodb://user:pass@localhost:1234/db"
  (let [matcher (re-matcher #"^.*://(.*?):(.*?)@(.*?):(\d+)/(.*)$" url)]
    (when (.find matcher)
      (zipmap [:match :user :pass :host :port :db] (re-groups matcher))))) ; }}}

(defn init-mongodb [] ; {{{
  "Checks if connection and collection exist, otherwise initialize."
  (when (not (connection? *mongo-config*))
    (let [mongo-url (get (System/getenv) "MONGOHQ_URL" "mongodb://:@localhost:27017/heroku_template")
          config    (split-mongo-url mongo-url)]
      (println "Initializing mongo @ " mongo-url)
      (mongo! :db (:db config) :host (:host config) :port (Integer. (:port config)))
      (authenticate (:user config) (:pass config))
      (init-mongo-collection)))) ; }}}

(defn- init-mongo-collection []
  (if-not (collection-exists? :firstcollection)
    (create-collection! :firstcollection)))
