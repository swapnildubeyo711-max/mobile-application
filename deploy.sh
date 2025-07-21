#!/bin/bash

# Hotel Finder 3D - Deployment Script
# This script automates the deployment of the Hotel Finder 3D app

echo "🏨 Hotel Finder 3D - Deployment Script"
echo "======================================="

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
PURPLE='\033[0;35m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

print_header() {
    echo -e "${PURPLE}$1${NC}"
}

# Check if git is available
if ! command -v git &> /dev/null; then
    print_error "Git is not installed. Please install Git first."
    exit 1
fi

print_header "🚀 Starting deployment process..."

# Step 1: Check Git status
print_status "Checking Git status..."
if [ -n "$(git status --porcelain)" ]; then
    print_warning "You have uncommitted changes. Committing them now..."
    
    # Add all files
    git add .
    
    # Commit with timestamp
    TIMESTAMP=$(date "+%Y-%m-%d %H:%M:%S")
    git commit -m "🚀 Deploy Hotel Finder 3D - $TIMESTAMP

    Complete deployment package includes:
    - Beautiful HTML landing page with animations
    - Comprehensive press kit for media coverage
    - Jekyll configuration for GitHub Pages
    - All app source code and documentation
    - Visual demos and design showcase
    - Build instructions and deployment guide"
    
    print_success "Changes committed successfully!"
else
    print_success "Working directory is clean."
fi

# Step 2: Push to GitHub
print_status "Pushing to GitHub..."
git push origin HEAD

if [ $? -eq 0 ]; then
    print_success "Successfully pushed to GitHub!"
else
    print_error "Failed to push to GitHub. Please check your connection and permissions."
    exit 1
fi

# Step 3: Display deployment information
print_header "🎉 Deployment Complete!"
echo ""
print_success "Your Hotel Finder 3D app is now published online!"
echo ""

print_header "📱 Live Website:"
echo "https://swapnildubeyo711-max.github.io/mobile-application/"
echo ""

print_header "💻 GitHub Repository:"
echo "https://github.com/swapnildubeyo711-max/mobile-application"
echo ""

print_header "🎨 App Branch:"
echo "https://github.com/swapnildubeyo711-max/mobile-application/tree/cursor/develop-android-3d-hotel-booking-app-8167"
echo ""

print_header "📋 Available Resources:"
echo "✅ Interactive landing page with animations"
echo "✅ Complete Android app source code"
echo "✅ 3D OpenGL ES hotel visualization"
echo "✅ Single-letter city search functionality"
echo "✅ Price comparison across platforms"
echo "✅ Material Design 3 interface"
echo "✅ Build instructions and documentation"
echo "✅ Visual demo and design showcase"
echo "✅ Comprehensive press kit"
echo "✅ GitHub Pages deployment"
echo ""

print_header "🎯 Next Steps:"
echo "1. Visit the live website to see your app in action"
echo "2. Share the GitHub repository with developers"
echo "3. Use the press kit for media coverage"
echo "4. Build the APK using the provided instructions"
echo "5. Distribute the app to users"
echo ""

print_header "📱 Features Highlighted:"
echo "🔍 Single-Letter Search: Type 'L' → Find London, LA, Lisbon"
echo "🎮 3D Visualization: Interactive OpenGL ES hotel models"
echo "💰 Price Comparison: Booking.com, Expedia, Hotels.com"
echo "🏨 Famous Hotels: Ritz, Plaza, Burj Al Arab, Beverly Hills"
echo "🎨 Modern Design: Material Design 3 with purple/gold theme"
echo "📱 Professional UI: Smooth animations and intuitive UX"
echo ""

print_success "Hotel Finder 3D is now live and ready to revolutionize hotel booking! 🏨✨"
echo ""
print_status "Thank you for using Hotel Finder 3D deployment script!"
echo "Experience hotels like never before! 🌟"