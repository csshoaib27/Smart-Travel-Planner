FROM node:18-alpine AS builder

WORKDIR /build
COPY frontend/package*.json ./
RUN npm install --legacy-peer-deps

COPY frontend . 
RUN npm run build

FROM nginx:alpine

COPY --from=builder /build/dist/smart-travel-planner /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 4200

CMD ["nginx", "-g", "daemon off;"]
