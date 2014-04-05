(ns webdev.item.handler
  (:require [webdev.item.model :refer [create-doit read-doit read-doits]]))

(defn handle-index-items
  [req]
  (let [doits (read-doits)]
    {:status 200
     :headers {}
     :body (str "<html><head></head><body><div>"
                (mapv :name doits)
                "</div><form method=</body><html>")}))
