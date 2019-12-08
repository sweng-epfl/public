# DO NOT EDIT THIS SCRIPT
# YOU SHOULD PROBABLY NOT RUN THIS SCRIPT EITHER, AS IT MOVES MULTIPLE FILES IN YOUR BUILD
# SWENG STAFF WILL NOT TAKE LIABILITY IF YOU RUN THIS SCRIPT ANYWAY

if [[ ! -v TRAVIS ]];
then
  echo "WARNING: This script will move files around. Although it puts them back in place afterwards, it may damage your work"
  echo "If you really want to run this on your machine, please make sure that you have commited your code before."
  echo "The Sweng team will take no responsibility if the script fails and removes your work."

  read -p 'Please confirm that you agree by typing "yes": ' res

  if [ $res = 'yes' ]
  then
    echo
    echo
  else
    echo "Cancelling."
    exit 0
  fi
fi

echo "Checking that your tests can run on our code..."
sh src/smoke/smokeCheck.sh