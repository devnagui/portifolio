#!/bin/bash

echo "Configuring line ending for Linux.."
git config --global core.autocrlf input

echo "Configuring line ending for Windows.."
git config --global core.autocrlf true 

echo "Configuring pushing for single branch.."
git config --global push.default simple

echo "Configuring pull with '--rebase' default.."
git config --global pull.rebase true

echo "Configuring 'rerere' for reused recorded resolution(less merging).."
git config --global rerere.enabled true

echo "Configuring Colored UI for git .."
git config --global color.ui true

echo "Configuring alias 's' for 'status -s' .."
git config alias.s "status -s"

echo "Configuring alias 'lg' for 'log decoration' .."
git config alias.lg "log --oneline --decoration --all --graph"

echo "Configuring username and email"
git config --global user.email "dev.nathan.guimaraes@gmail.com"
git config --global user.name "devnagui"



echo "End of basic configuration.."

