(ns server.handler
  (:require [blockserver.handler :as bh]
            [todoschema.validator :refer :all]
            [clojure.spec.alpha :as s]))

(def app (bh/app (fn [body] (if (s/valid? :todo/todo body)
                              {:status 200 :body body}
                              {:status 400 :body (s/explain-data :todo/todo body)}))))
