# Manual deploy script for GitHub Pages.
# Run this from the Frontend directory.

Write-Host "Installing dependencies..."
npm ci

Write-Host "Building production bundle..."
npm run build:prod

Write-Host "Deploying to GitHub Pages..."
npx angular-cli-ghpages --dir=dist/smart-travel-planner --repo=https://github.com/csshoaib27/Smart-Travel-Planner.git --branch=gh-pages

Write-Host "Deployment complete. Visit https://csshoaib27.github.io/Smart-Travel-Planner/"
