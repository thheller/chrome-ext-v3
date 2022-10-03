(ns demo.sw
  (:require [shadow.cljs.modern :refer (js-await)]))

(defn on-alarm []
  (js/chrome.action.setBadgeText #js {:text ""})
  (js/chrome.notifications.create
    (clj->js
      {:type "basic"
       :iconUrl "/stay_hydrated.png"
       :title "Time to hydrate"
       :message "Everyday I'm Guzzlin'!"
       :buttons [{:title "Keep it Flowing."}]
       :priority 0}))

  (js/console.log "on-alarm done"))

(defn init []
  (js/chrome.alarms.onAlarm.addListener on-alarm)
  (js/chrome.notifications.onButtonClicked.addListener
    (fn []
      (js-await [^js item (js/chrome.storage.sync.get #js ["minutes"])]
        (js/chrome.action.setBadgeText #js {:text "ON"})
        (js/chrome.alarms.create #js {:delayInMinutes (.-minutes item)})
        )))

  (js/console.log "sw init done"))