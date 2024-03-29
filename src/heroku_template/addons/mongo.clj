(ns heroku-template.addons.mongo
  (:use
    somnium.congomongo
    [somnium.congomongo.config :only [*mongo-config*]]
    [clojure.data.json :only [json-str]]))

(declare init-mongo-collection)

(def heroku-template-collections [:firstcollection])

(defn- split-mongo-url [url] ; {{{
  "Parses mongodb url from heroku, eg. mongodb://user:pass@localhost:1234/db"
  (let [matcher (re-matcher #"^.*://(.*?):(.*?)@(.*?):(\d+)/(.*)$" url)]
    (when (.find matcher)
      (zipmap [:match :user :pass :host :port :db] (re-groups matcher))))) ; }}}

(defn init-mongodb [& {:keys [local-dbname] :or {local-dbname "heroku-template"}}] ; {{{
  "Checks if connection and collection exist, otherwise initialize."
  (when (not (connection? *mongo-config*))
    (let [mongo-url (get (System/getenv) "MONGOHQ_URL" (str "mongodb://:@localhost:27017/" local-dbname))
          config    (split-mongo-url mongo-url)]
      (println "Initializing mongo @ " mongo-url)
      (mongo! :db (:db config) :host (:host config) :port (Integer. (:port config)))
      (authenticate (:user config) (:pass config))

      (doseq [col heroku-template-collections]
        (if-not (collection-exists? col)
          (create-collection! col)))))) ; }}}

(defn- id-conv [obj]
  (if-let [id (:_id obj)] (assoc obj :_id (str id)) obj))

(defn mongo->json [obj]
  (json-str (if (sequential? obj) (map id-conv obj) (id-conv obj))))

