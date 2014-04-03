(ns webdev.item.model
  (:use [monger.core :only [connect! set-db! get-db]]
        [monger.result :only [ok?]]
        [validateur.validation])
  (:require [monger.collection :as collection]
            [monger.util :as util]
            [monger.joda-time]
            [monger.json]
            [clj-time.core :as time]))

(def mongo-options
  {:host "localhost"
   :port 27017
   :db "doitnow"
   :doits-collection "doits"})

(connect! mongo-options)

(set-db! (get-db (mongo-options :db)))

(defn- with-oid
  [doit]
  (assoc doit :_id (util/object-id)))

(defn- created-now
  [doit]
  (assoc doit :created (time/now)))

(defn- modified-now
  [doit]
  (assoc doit :modified (time/now)))

(def doit-validator (validation-set
                      (presence-of :_id)
                      (presence-of :title)
                      (presence-of :created)
                      (presence-of :modified)))

(defn create-doit
  [doit]
  (let [new-doit (created-now (modified-now (with-oid doit)))]
    (if (valid? doit-validator new-doit)
      (if (ok? (collection/insert (mongo-options :doits-collection) new-doit))
        new-doit
        (throw (Exception. "Write Failed")))
      (throw (IllegalArgumentException.)))))

(defn read-doit
  [id]
  (collection/find-map-by-id (mongo-options :doits-collection) id))
