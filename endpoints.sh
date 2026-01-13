#!/bin/bash

USER="username"
PASS="password"
BASE_URL="http://localhost:8080"

# Login endpoint
login() {
  echo "Calling login..."
  curl -X GET "$BASE_URL/login" -u $USER:$PASS
  echo
}

# Get incomplete todos
incomplete_todos() {
  echo "Fetching incomplete todos..."
  curl -X GET "$BASE_URL/api/incomplete-todos" -u $USER:$PASS | jq
  echo
}

expired_todos() {
  echo "Fetching expired todos..."
  curl -X GET "$BASE_URL/api/expired-todos" -u $USER:$PASS | jq
  echo
}


start_todo() {
  echo "Starting todo..."
  curl -X PUT "$BASE_URL/api/start-todo?id=1" -u $USER:$PASS | jq
  echo
}

pause_todo() {
  echo "Pausing todo..."
  curl -X PUT "$BASE_URL/api/pause-todo?id=1" -u $USER:$PASS | jq
  echo
}


weekly_leaderboards() {
  echo "Loading weekly leaderboards..."
  curl -X GET "$BASE_URL/api/weekly-leaderboards" -u $USER:$PASS | jq
  echo
}
## Another endpoint example
#get_all_todos() {
#  echo "Fetching all todos..."
#  curl -X GET "$BASE_URL/todos" -u $USER:$PASS
#  echo
#}

# call functions based on CLI args
for cmd in "$@"; do
  $cmd
done
