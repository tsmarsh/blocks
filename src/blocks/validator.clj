(ns blocks.validator
  (:require [clojure.spec.alpha :as s]))

(s/def :owner/forename string?)
(s/def :owner/surname string?)

(s/def :todo/title string?)
(s/def :todo/description string?)

(s/def :item/status string?)
(s/def :items/description string?)

(s/def :items/item (s/keys :req-un [:item/status :item/description]))
(s/def :todo/items (s/coll-of :items/item) )
(s/def :todo/owner (s/keys :req-un [:owner/forename :owner/surname]))
(s/def :todo/todo (s/keys :req-un [:todo/owner]))
