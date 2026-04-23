#!/bin/bash
# Manual deploy script for GitHub Pages.
# Run this from the Frontend directory.

set -e

echo "Installing dependencies..."
npm ci

echo "Building production bundle..."
npm run build:prod

echo "Deploying to GitHub Pages..."
npx angular-cli-ghpages --dir=dist/smart-travel-planner --repo=https://github.com/csshoaib27/Smart-Travel-Planner.git --branch=gh-pages

echo "Deployment complete. Visit https://csshoaib27.github.io/Smart-Travel-Planner/"
