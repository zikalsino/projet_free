import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';

const Report = () => {
  const [report, setReport] = useState({
    title: '',
    description: '',
    contenu: '',
    createdDate: '',
  });
  const [successMessage, setSuccessMessage] = useState(''); // État pour gérer le message de succès
  const { id } = useParams();
  const navigate = useNavigate(); // Utilisation de useNavigate pour la navigation

  useEffect(() => {
    if (id) {
      // Si un ID est passé, charger les données du rapport pour modification
      axios.get(`http://localhost:8080/api/reports/${id}`)
        .then(response => {
          setReport(response.data);
        })
        .catch(error => {
          console.error('Erreur lors du chargement du rapport:', error);
        });
    }
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setReport(prevReport => ({
      ...prevReport,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
  
    // Crée une copie du rapport sans l'ID pour l'insertion d'un nouveau rapport
    const { id, ...reportData } = report;
  
    if (id) {
      // Si un ID existe, mettre à jour le rapport
      axios.put(`http://localhost:8080/api/reports/${id}`, reportData)
        .then(() => {
          setSuccessMessage('Le rapport a été mis à jour avec succès !'); // Afficher le message de succès
          setTimeout(() => navigate('/report-generator'), 2000); // Rediriger après 2 secondes pour voir le message
        })
        .catch(error => {
          console.error('Erreur lors de la mise à jour du rapport:', error);
        });
    } else {
      // Sinon, créer un nouveau rapport sans envoyer l'ID
      axios.post('http://localhost:8080/api/reports/create', reportData)
        .then(() => {
          setSuccessMessage('Le rapport a été créé avec succès !'); // Afficher le message de succès
          setTimeout(() => navigate('/report-generator'), 2000); // Rediriger après 2 secondes pour voir le message
        })
        .catch(error => {
          console.error('Erreur lors de la création du rapport:', error);
        });
    }
  };

  return (
    <div>
      <h1>{id ? 'Modifier le Rapport' : 'Créer un Rapport'}</h1>
      {successMessage && <div className="alert alert-success">{successMessage}</div>} {/* Affichage du message de succès */}
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="title">Titre</label>
          <input
            type="text"
            id="title"
            name="title"
            value={report.title}
            onChange={handleChange}
            className="form-control"
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="description">Description</label>
          <input
            type="text"
            id="description"
            name="description"
            value={report.description}
            onChange={handleChange}
            className="form-control"
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="contenu">Contenu</label>
          <textarea
            id="contenu"
            name="contenu"
            value={report.contenu}
            onChange={handleChange}
            className="form-control"
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="createdDate">Date de création</label>
          <input
            type="date"
            id="createdDate"
            name="createdDate"
            value={report.createdDate}
            onChange={handleChange}
            className="form-control"
            required
          />
        </div>
        <button type="submit" className="btn btn-primary">
          {id ? 'Mettre à jour' : 'Créer'}
        </button>
      </form>
    </div>
  );
};

export default Report;
