(ns heroku-template.test.core
  (:use heroku-template.core
        heroku-template.addons.mongo
        somnium.congomongo)
  (:use [clojure.test]))

(defmacro deftest* [name & expr] ; {{{
  `(deftest ~name
     (doseq [colname# heroku-template-collections]
       (drop-coll! colname#))
     ~@expr)) ; }}}

(init-mongodb :local-dbname "heroku-template-test")

(deftest replace-me
  (insert! :test {:hello "world"})
  (is (= "world" (:hello (fetch-one :test)))))
