// Animals API JavaScript - Fetch functions for Animal CRUD operations
const API_BASE_URL = 'http://localhost:8080/animals';

// Utility function to handle API responses
async function handleResponse(response) {
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    return await response.json();
}

// GET all animals with cache busting
async function getAllAnimals() {
    try {
        // Add cache busting parameter
        const timestamp = new Date().getTime();
        const response = await fetch(`${API_BASE_URL}?_t=${timestamp}`);
        return await handleResponse(response);
    } catch (error) {
        console.error('Error fetching all animals:', error);
        throw error;
    }
}

// GET animal by ID with cache busting
async function getAnimalById(animalId) {
    console.log('getAnimalById called with ID:', animalId);
    
    try {
        // Add cache busting parameter
        const timestamp = new Date().getTime();
        const response = await fetch(`${API_BASE_URL}/${animalId}?_t=${timestamp}`);
        const result = await handleResponse(response);
        console.log('getAnimalById response:', result);
        return result;
    } catch (error) {
        console.error(`Error fetching animal with ID ${animalId}:`, error);
        throw error;
    }
}

// GET animals by breed
async function getAnimalsByBreed(breed) {
    try {
        const response = await fetch(`${API_BASE_URL}/breed/${encodeURIComponent(breed)}`);
        return await handleResponse(response);
    } catch (error) {
        console.error(`Error fetching animals by breed ${breed}:`, error);
        throw error;
    }
}

// GET animals by name (search) - corrected to use query parameter 'key'
async function getAnimalsByName(name) {
    try {
        const response = await fetch(`${API_BASE_URL}/name?key=${encodeURIComponent(name)}`);
        return await handleResponse(response);
    } catch (error) {
        console.error(`Error searching animals by name ${name}:`, error);
        throw error;
    }
}

// GET heavy animals - corrected default weight and parameter handling
async function getHeavyAnimals(weight = 50.0) {
    try {
        const response = await fetch(`${API_BASE_URL}/heavy?weight=${weight}`);
        return await handleResponse(response);
    } catch (error) {
        console.error(`Error fetching heavy animals with weight > ${weight}:`, error);
        throw error;
    }
}

// CREATE - Add new animal
async function createAnimal(animalData) {
    console.log('createAnimal called with data:', animalData);
    
    try {
        const response = await fetch(API_BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(animalData)
        });
        const result = await handleResponse(response);
        console.log('createAnimal response:', result);
        return result;
    } catch (error) {
        console.error('Error creating animal:', error);
        throw error;
    }
}

// UPDATE - Update existing animal
async function updateAnimal(animalId, animalData) {
    console.log('updateAnimal called with ID:', animalId);
    console.log('updateAnimal called with data:', animalData);
    
    try {
        const response = await fetch(`${API_BASE_URL}/${animalId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(animalData)
        });
        const result = await handleResponse(response);
        console.log('updateAnimal response:', result);
        return result;
    } catch (error) {
        console.error(`Error updating animal with ID ${animalId}:`, error);
        throw error;
    }
}

// DELETE - Delete animal
async function deleteAnimal(animalId) {
    try {
        const response = await fetch(`${API_BASE_URL}/${animalId}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        // Return the updated list of animals after deletion
        return await handleResponse(response);
    } catch (error) {
        console.error(`Error deleting animal with ID ${animalId}:`, error);
        throw error;
    }
}

// Helper function to get the correct image path - completely dynamic, no hardcoding
function getAnimalImagePath(animal) {
    if (!animal) {
        return './images/placeholder.jpg';
    }
    
    // PRIORITY 1: Check if animal has a custom image URL first
    if (animal.imageUrl && animal.imageUrl.trim() !== '') {
        console.log('🎯 USING CUSTOM IMAGE URL for', animal.name + ':', animal.imageUrl);
        console.log('🎯 Full animal object:', animal);
        return animal.imageUrl.trim();
    }
    
    // PRIORITY 2: Fall back to breed-based image mapping
    if (!animal.breed) {
        console.log('⚠️ No breed found for', animal.name + ', using placeholder');
        return './images/placeholder.jpg';
    }
    
    console.log('📁 Using breed-based image for', animal.name + ', breed:', animal.breed);
    console.log('📁 Animal imageUrl field value:', animal.imageUrl);
    
    // Convert breed name to match image file naming pattern
    const breed = animal.breed.trim().toLowerCase();
    
    // Special case for chimpanzee since image is named "Baby_Chimp.jpg"
    if (breed.includes('chimp')) {
        return './images/Baby_Chimp.jpg';
    }
    
    // Map common breed names to actual image files
    const breedImageMap = {
        'penguin': 'Penguin.jpg',
        'giraffe': 'Giraffe.jpg', 
        'capybara': 'Capybara.jpg',
        'crocodile': 'Crocodile.jpg',
        'chimpanzee': 'Baby_Chimp.jpg'
    };
    
    // Check if we have a direct mapping
    if (breedImageMap[breed]) {
        return `./images/${breedImageMap[breed]}`;
    }
    
    // Fallback: try the capitalized version of the breed name
    const capitalizedBreed = breed.charAt(0).toUpperCase() + breed.slice(1);
    return `./images/${capitalizedBreed}.jpg`;
}

// Helper function to display animals in HTML
function displayAnimals(animals, containerId) {
    const container = document.getElementById(containerId);
    if (!container) {
        console.error(`Container with ID ${containerId} not found`);
        return;
    }
    
    container.innerHTML = '';
    
    if (!animals || animals.length === 0) {
        container.innerHTML = '<p>No animals found.</p>';
        return;
    }
    
    animals.forEach(animal => {
        const animalCard = createAnimalCard(animal);
        container.appendChild(animalCard);
    });
}

// Helper function to create animal card HTML element - completely dynamic
function createAnimalCard(animal) {
    const animalDiv = document.createElement('div');
    animalDiv.className = 'Animal_List';
    
    const imagePath = getAnimalImagePath(animal);
    
    // Add cache busting to image URLs
    const imagePathWithCacheBuster = imagePath.includes('http') 
        ? `${imagePath}?_t=${new Date().getTime()}` 
        : imagePath;
    
    animalDiv.innerHTML = `
        <img src="${imagePathWithCacheBuster}" alt="${animal.name}" onerror="this.src='./images/placeholder.jpg'" />
        <h2>${animal.name}</h2>
        <p><i><b>${animal.breed || 'Unknown breed'}</b></i></p>
        <p><strong>Weight:</strong> ${animal.weight} kg</p>
        <h3><a href="./details.html?id=${animal.animalId}">View details of ${animal.name}!</a></h3>
    `;
    return animalDiv;
}

// Helper function to get URL parameter
function getUrlParameter(name) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(name);
}

// Helper function to show loading state
function showLoading(containerId) {
    const container = document.getElementById(containerId);
    if (container) {
        container.innerHTML = '<p>Loading animals...</p>';
    }
}

// Helper function to show error state
function showError(containerId, error) {
    const container = document.getElementById(containerId);
    if (container) {
        container.innerHTML = `<p>Error loading animals: ${error.message}</p>`;
    }
}