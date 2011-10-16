(ns heroku-template.addons.memcache
  (:use clj-spymemcached.core))

(defn init-memcache []
  (let [host (get (System/getenv) "MEMCACHE_SERVERS" "localhost")
        user (get (System/getenv) "MEMCACHE_USERNAME" "")
        password (get (System/getenv) "MEMCACHE_PASSWORD" "")]
    (memcached! :host host :user user :password password)))


