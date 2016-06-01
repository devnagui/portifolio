#!/bin/bash

echo "Configuring line ending for Linux.."
git config --global core.autocrlf input

echo "Configuring line ending for Windows.."
git config --global core.autocrlf true 

echo "Configuring pushing for single branch.."
git config --global push.default simple

echo "Configuring pull with '--rebase' default.."
git config --global pull.rebase true

echo "Configuring 'rerere' for automatic rebasing between branches.."
git config --global rerere.enabled true

echo "Configuring Colored UI for git .."
git config --global color.ui true

echo "End of basic configuration.."

