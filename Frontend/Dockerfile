# Build stage
FROM node:20-alpine AS builder

WORKDIR /app

# Copy package files
COPY package.json package-lock.json ./

# Install dependencies
RUN npm ci

# Copy source code
COPY . .

# Build the Angular application
RUN npm run build

# Runtime stage
FROM nginx:alpine

# Copy nginx config
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d/

# Copy built application from builder stage
COPY --from=builder /app/dist/smart-travel-planner/browser /usr/share/nginx/html

# Expose port
EXPOSE 4200

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD wget --quiet --tries=1 --spider http://localhost:4200/ || exit 1

# Start nginx
CMD ["nginx", "-g", "daemon off;"]
