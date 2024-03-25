# Git

Main useful git commands with [oh-my-zsh](https://kapeli.com/cheat_sheets/Oh-My-Zsh_Git.docset/Contents/Resources/Documents/index) shortcuts.

```bash
# Clone: git clone --recurse-submodules <repo>
gcl <repo>

# Git status
gst

# Checkout into a new branch: git checkout -b <branch>
gcb <branch>

# Checkout into an existing branch: git checkout <branch>
gco <branch>

# Stage all changes: git add --all
gaa

# Commit: git commit -m <message>
gcmsg <message>

# Commit and amend: git commit -v --amend
gc!

# Push to origin (same branch name): git push --force origin $(current_branch)
ggf

# Checkout to main branch: git checkout $(git_main_branch)
gcm

# Pull and rebase: git pull --rebase
gpr

# Rebase interactive: git rebase -i <branch>
grbi <branch>
grbi HEAD~5

# Rebase continue: git rebase --continue
grbc

# Rebase interactive with main: git rebase $(git_main_branch)
grbm

# Reset hard (dismiss all changes) to previous commit or specific commit: git reset --hard
grhh
grhh <commit>

# List commits: git log --graph --pretty='%Cred%h%Creset -%C(auto)%d%Creset %s %Cgreen(%ar) %C(bold blue)<%an>%Creset'
glol

# List configuration: git config --list
gcf
```